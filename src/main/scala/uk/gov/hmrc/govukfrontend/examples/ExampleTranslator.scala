/*
 * Copyright 2021 HM Revenue & Customs
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

import java.nio.file.Paths

import fastparse.NoWhitespace._
import fastparse.Parsed.{Success => PSuccess}
import fastparse._
import uk.gov.hmrc.govukfrontend.examples.FileSystem.{TrueDir, TrueFile, prepareDirStructure}
import uk.gov.hmrc.govukfrontend.examples.NunjucksParser.nunjucksParser
import uk.gov.hmrc.govukfrontend.examples.PlayVersions.{Play26, Play27, PlayVersion}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

sealed trait ExampleType {
  override def toString: String = this.getClass.getSimpleName.toLowerCase.stripSuffix("$")
}
case object GovukFrontend extends ExampleType
case object HmrcFrontend extends ExampleType

object ExampleTranslator {

  case class Draft(content: String, path: TrueFile)

  def translateTwirlExamples(
    srcNunjucksExamplesDir: TrueDir,
    destTwirlExamplesDirPath: TrueDir,
    exampleType: ExampleType)(implicit ec: ExecutionContext): Future[Unit] = {

    def getNunjucksExamples: Future[List[TrueFile]] = Future {
      val nunjucksExamples: List[TrueFile] = for {
        file <- srcNunjucksExamplesDir.recursiveContents.toList
        if file.path.toString.contains(".njk") && !file.path.toString.contains(".md.njk")
      } yield file

      if (nunjucksExamples.isEmpty)
        throw new Exception("No Nunjucks examples files found")
      else {
        println(s"\nFound ${nunjucksExamples.length} Nunjucks examples to translate\n")
        nunjucksExamples
      }
    }

    def allocateTwirlExamplePath(srcNunjucksExampleFilePath: TrueFile, playVersion: PlayVersion): Future[TrueFile] =
      Future {
        val absPath: String = srcNunjucksExampleFilePath.path.toAbsolutePath.toString
        val relPath: String = absPath.replace(s"${srcNunjucksExamplesDir.path.toAbsolutePath}/", "")

        def subDirP[_: P]: P[String] = P(CharsWhile(_ != '/').! ~ "/")

        def nunjucksFileP[_: P]: P[String] = P((!".njk" ~ AnyChar).rep ~ ".njk").!

        def nunjucksPathP[_: P]: P[(List[String], String)] = P(subDirP.rep(1).map(_.toList) ~ nunjucksFileP ~ End)

        parse(relPath, nunjucksPathP(_)) match {

          case PSuccess((component :: scenario :: Nil, nunjucksPath), _) => {
            nunjucksPath match {
              case validPath if nunjucksPath == "index.njk" || nunjucksPath == "code.njk" =>
                var filename = scenario.split("-").toList.map(_.capitalize).mkString("")
                filename = filename.substring(0, 1).toLowerCase + filename.substring(1)
                TrueFile(
                  Paths.get(
                    s"${destTwirlExamplesDirPath.path}/play-${playVersion.version}/twirl/uk/gov/hmrc/${exampleType.toString}/views/examples/${
                      component
                        .replaceAll("-", "")
                    }/$filename.scala.html")
                )
              case _ =>        throw new Exception(
                s"""Expected Nunjucks file name should be either index.njk OR code.njk'.
                   |Instead found: [$absPath].""".stripMargin)
            }
          }

          case _ =>
            throw new Exception(
              s"""Expected Nunjucks example path to be of format '(pathToExamples)/componentName/scenario/index.njk'.
               |Instead found: [$absPath].""".stripMargin)
        }
      }

    def translateToTwirlExample(srcNjksExampleFilePath: TrueFile, playVersion: PlayVersion): Future[Try[String]] = {
      val njksContentToBe: Future[String] = srcNjksExampleFilePath.read

      njksContentToBe.flatMap { njksContent =>
        Future {

          val template: Try[NunjucksTemplate] = Try(
            parse(njksContent, nunjucksParser(_)).get.value
          ).transform(
            Success.apply,
            e => Failure(new Exception(s"""Failed to interpret Nunjucks example at [${srcNjksExampleFilePath.path}].
                     |Details: [${e.getMessage}]""".stripMargin))
          )

          template
            .transform(
              ex =>
                Success(
                  playVersion match {
                    case Play26() => TwirlFormatter.format(ex)
                    case Play27() => TwirlFormatter.format(ex)
                  }
              ),
              e => {
                val errorMessage =
                  s"""Failed to convert parsed Nunjucks example code at [${srcNjksExampleFilePath.path}] to a Twirl example for $playVersion.
                         |Details: [${e.getMessage}]\n""".stripMargin
                println(errorMessage)
                Failure(new Exception(errorMessage))
              }
            )
        }
      }
    }

    if (!srcNunjucksExamplesDir.exists())
      throw new Exception(s"Failed to find source of Nunjucks examples at [${srcNunjucksExamplesDir.path}].")
    else {

      val draftsToBe: Future[List[Draft]] = getNunjucksExamples.flatMap { njksExamples =>
        Future.sequence {
          for {
            srcExample  <- njksExamples
            playVersion <- PlayVersions.implementedPlayVersions
            destPathToBe    = allocateTwirlExamplePath(srcExample, playVersion)
            destContentToBe = translateToTwirlExample(srcExample, playVersion)
          } yield
            for {
              destPath    <- destPathToBe
              destContent <- destContentToBe
              content = destContent.getOrElse {
                println(s"Twirl not generated for $destPath")
                ""
              }
            } yield Draft(content, destPath)
        }
      }

      val dirClearing: Future[Unit] =
        Future {
          TrueDir(destTwirlExamplesDirPath.path.resolve(Play26.toString())).del()
        }
      val dirCreation: Future[Unit] = draftsToBe.flatMap(files => prepareDirStructure(files.map(_.path)))

      val writeOpsToBe: Future[List[Unit]] = for {
        _       <- dirClearing
        _       <- dirCreation
        drafts  <- draftsToBe
        writeOp <- Future.sequence(drafts.map(d => d.path.write(d.content)))
      } yield {
        val translateCount = drafts.count(_.content != "")
        println(s"\nTranslated $translateCount Twirl examples\n")
        writeOp
      }

      for (writeOps <- writeOpsToBe) yield for (_ <- writeOps) yield ()
    }

  }

}
