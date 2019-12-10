/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.govukfrontend.examples

import java.io.{PrintWriter, File => JFile}

import fastparse.NoWhitespace._
import fastparse.Parsed.{Success => PSuccess}
import fastparse._
import uk.gov.hmrc.govukfrontend.examples.NunjucksParser.nunjucksParser
import uk.gov.hmrc.govukfrontend.examples.PlayVersions.{Play25, Play26, PlayVersion}

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source
import scala.reflect.io.Directory
import scala.util.{Failure, Success, Try}

object ExampleTranslator {

  trait InvalidNunjucksExamplePath { self: Throwable =>
  }
  trait FailedToDeletePriorTwirlExamplesPath { self: Throwable =>
  }
  trait FailedToCreateDestinationTwirlExamplesPath { self: Throwable =>
  }
  trait FailedToParseNunjucksExample { self: Throwable =>
  }
  trait FailedToConvertNunjucksCodeToTwirl { self: Throwable =>
  }
  trait UnexpectedNunjucksExampleNamingConvention { self: Throwable =>
  }

  def translateTwirlExamples(srcNunjucksExamplesDir: JFile, destTwirlExamplesDirPath: JFile)(
    implicit ec: ExecutionContext): Future[Unit] = {

    def createEmptyDestTwirlExamplesFolder(): Future[Unit] = Future {
      if (destTwirlExamplesDirPath.exists())
        try destTwirlExamplesDirPath.delete()
        catch {
          case e: Exception =>
            throw new Exception(
              s"Failed to delete existing Twirl examples directory at [${destTwirlExamplesDirPath.getAbsolutePath}] to replace with new examples. Details: [${e.getMessage}].")
              with FailedToDeletePriorTwirlExamplesPath
        }
      try destTwirlExamplesDirPath.mkdirs
      catch {
        case e: Exception =>
          throw new Exception(
            s"Failed to create new Twirl examples directory at [${destTwirlExamplesDirPath.getAbsolutePath}] in which to generate examples. Details: [${e.getMessage}].")
            with FailedToCreateDestinationTwirlExamplesPath
      }
    }

    def getNunjucksExamples: Future[List[JFile]] = Future {
      val nunjucksExamples: List[JFile] = for {
        file <- new Directory(srcNunjucksExamplesDir).deepFiles.toList
        if file.path.contains(".njk") && !file.path.contains(".md.njk")
      } yield file.jfile

      if (nunjucksExamples.isEmpty) throw new Exception("No Nunjucks examples files found")
      else
        nunjucksExamples
    }

    def allocateTwirlExamplePath(srcNunjucksExampleFilePath: JFile, playVersion: PlayVersion): Future[JFile] = Future {
      val absPath: String = srcNunjucksExampleFilePath.getPath
      val relPath: String = absPath.replace(s"${srcNunjucksExamplesDir.getAbsolutePath}/", "")

      def subDirP[_: P]: P[String] = P(CharsWhile(_ != '/').! ~ "/")

      def nunjucksFileP[_: P]: P[String] = P((!".njk" ~ AnyChar).rep ~ ".njk").!

      def nunjucksPathP[_: P]: P[(List[String], String)] = P(subDirP.rep(1).map(_.toList) ~ nunjucksFileP ~ End)

      val e = new Exception(
        s"Expected Nunjucks example path to be of format '(pathToExamples)/componentName/scenario/index.njk'. Instead found: [$absPath].")
        with UnexpectedNunjucksExampleNamingConvention

      parse(relPath, nunjucksPathP(_)) match {
        case PSuccess((component :: scenario :: Nil, "index.njk"), _) =>
          new JFile(s"${destTwirlExamplesDirPath.getAbsolutePath}/play-${playVersion.version}/$component/$scenario.scala.html")
        case _ => throw e
      }
    }

    def readFile(file: JFile): String = {
      val source = Source.fromFile(file, "UTF-8")
      try {
        source.getLines().mkString("\n")
      } finally {
        source.close()
      }
    }

    def translateToTwirlExample(srcNjksExampleFilePath: JFile, playVersion: PlayVersion): Future[String] = Future {

      val template: Try[NunjucksTemplate] = Try(
        parse(readFile(srcNjksExampleFilePath), nunjucksParser(_)).get.value
      ).transform(
        Success.apply,
        e =>
          Failure(new Exception(
            s"Failed to interpret Nunjucks example at [${srcNjksExampleFilePath.getPath}]. Details: [${e.getMessage}]")
            with FailedToParseNunjucksExample)
      )

      val example: Try[String] = template
        .transform(ex =>
          Success(
            playVersion match {
              case Play25() => TwirlFormatter.formatPlay25(ex)
              case Play26() => TwirlFormatter.format(ex)
            }
          ),
          e => {
            e.printStackTrace()
            Failure(new Exception(
              s"Failed to convert parsed Nunjucks example code at [${srcNjksExampleFilePath.getPath}] to a Twirl example. Details: [${e.getMessage}]")
              with FailedToConvertNunjucksCodeToTwirl)
          }
        )

      example.get
    }

    def writeToDestDir(twirlExample: String, destFile: JFile): Unit = {
      val parentDirMaybe = destFile.getParentFile
      if (parentDirMaybe != null)
        destFile.getParentFile.mkdirs

      val pw = new PrintWriter(destFile)
      try {
        pw.write(twirlExample)
      } finally {
        pw.close()
      }
    }

    if (!srcNunjucksExamplesDir.exists())
      throw new Exception(s"Failed to find source of Nunjucks examples at [${srcNunjucksExamplesDir.getAbsolutePath}].")
        with InvalidNunjucksExamplePath
    else {
      val dirCreationToBe: Future[Unit] = createEmptyDestTwirlExamplesFolder()

      val njksExamplesToBe: Future[List[JFile]] = getNunjucksExamples.map(_.toList)

      def writeTwirls(): Future[Unit] = {
        val writeOpsToBe: Future[List[Unit]] = njksExamplesToBe
          .flatMap { njksExamples =>
            val writeOps = for {
              srcExample <- njksExamples
              playVersion <- PlayVersions.implementedPlayVersions
              destContentToBe = translateToTwirlExample(srcExample, playVersion)
              destPathToBe = allocateTwirlExamplePath(srcExample, playVersion)
            } yield for {
              destContent <- destContentToBe
              destPath <- destPathToBe
            } yield writeToDestDir(destContent, destPath)

            Future.sequence(writeOps)
          }

        for (writeOps <- writeOpsToBe) yield for (_ <- writeOps) yield ()
      }

      for {
        _ <- dirCreationToBe
        _ <- writeTwirls()
      } yield ()
    }

  }

}
