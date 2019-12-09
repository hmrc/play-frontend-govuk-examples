package uk.gov.hmrc.govukfrontend.examples

import java.io.{File => JFile}

import fastparse._
import fastparse.NoWhitespace._
import fastparse.Parsed.{Success => PSuccess}
import uk.gov.hmrc.govukfrontend.examples.NunjucksParser.nunjucksParser
import uk.gov.hmrc.govukfrontend.examples.PlayVersions.{Play25, Play26, PlayVersion}

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.io.{Directory, File}
import scala.util.{Failure, Success, Try}

class ExampleTranslator {

trait InvalidNunjucksExamplePath {self: Throwable => ()}
trait FailedToDeletePriorTwirlExamplesPath {self: Throwable => ()}
trait FailedToCreateDestinationTwirlExamplesPath  {self: Throwable => () }
trait FailedToParseNunjucksExample {self: Throwable => ()}
trait FailedToConvertNunjucksCodeToTwirl {self: Throwable => ()}

  def translateTwirlExamples(srcNjksExamplesDirPath: JFile, destTwirlExamplesDirPath: JFile)(implicit ec: ExecutionContext): Future[Unit] = Future {

    def createEmptyDestTwirlExamplesFolder(): Future[Unit] = Future {
      if (destTwirlExamplesDirPath.exists())
        try
          destTwirlExamplesDirPath.delete()
        catch {
          case e: Exception => throw new Exception(s"Failed to delete existing Twirl examples directory at [${destTwirlExamplesDirPath.getAbsolutePath}] to replace with new examples. Details: [${e.getMessage}].") with FailedToDeletePriorTwirlExamplesPath
        }
      try
        destTwirlExamplesDirPath.mkdirs
      catch {
        case e: Exception => throw new Exception(s"Failed to create new Twirl examples directory at [${destTwirlExamplesDirPath.getAbsolutePath}] in which to generate examples. Details: [${e.getMessage}].") with FailedToCreateDestinationTwirlExamplesPath
      }
    }

    def getNunjucksExamples: Future[Iterator[File]] = Future {
      for {
        file <- new Directory(srcNjksExamplesDirPath).deepFiles
        path = file.path
        if path.contains(".njk") && !path.contains(".md.njk")
      } yield file
    }

    def allocateTwirlExamplePath(srcNjksExampleFilePath: File, playVersion: PlayVersion): Future[JFile] = Future {
      val absPath: String = srcNjksExampleFilePath.path
      val relPath: String = absPath.replace(srcNjksExamplesDirPath.getAbsolutePath, "")

      def subDirP[_: P]: P[String] = P(CharsWhile(_ != '/').! ~ "/")
      def nunjucksFileP[_: P]: P[String] = P((!".njk" ~ AnyChar).rep ~ ".njk").!
      def nunjucksPathP[_: P]: P[(List[String], String)] = P(subDirP.rep(1).map(_.toList) ~ nunjucksFileP ~ End)

      val e = new Exception(s"Expected Nunjucks example path to be of format '(pathToExamples)/componentName/index.njk'. Instead found: [${absPath}].") with UnexpectedNunjucksExampleNamingConvention

      parse(relPath, nunjucksPathP(_)) match {
        case PSuccess((_::_, _), _) => throw e
        case PSuccess((componentName, "index.njk"), _) =>
          new JFile(s"${destTwirlExamplesDirPath.getAbsolutePath}/play-${playVersion.version}/$componentName.scala.html")
        case _ => throw e
      }
    }

    def readFile(file: File): String = ???

    def translateToTwirlExample(srcNjksExampleFilePath: File, destTwirlExamplePath: JFile, playVersion: PlayVersion): Future[Unit] = Future {

      val template: Try[NunjucksTemplate] = Try(
        parse(readFile(srcNjksExampleFilePath), nunjucksParser(_)).get.value
      ).transform(
        Success.apply, e => Failure(new Exception(s"Failed to interpret Nunjucks example at [${srcNjksExampleFilePath.path}]. Details: [${e.getMessage}]") with FailedToParseNunjucksExample)
      )

      val example: Try[String] = template
        .map(ex => playVersion match {
          case Play25() => TwirlFormatter.formatPlay25(ex)
          case Play26() => TwirlFormatter.format(ex)
        })
        .transform(Success.apply, e => Failure(new Exception(s"Failed to convert parsed Nunjucks example code at [${srcNjksExampleFilePath.path}] to a Twirl example. Details: [${e.getMessage}]") with FailedToConvertNunjucksCodeToTwirl))

      example.get
    }

    if (!srcNjksExamplesDirPath.exists())
      throw new Exception(s"Failed to find source of Nunjucks examples at [${srcNjksExamplesDirPath.getAbsolutePath}].") with InvalidNunjucksExamplePath)
    else {
      val dirCreation: Future[Unit] = createEmptyDestTwirlExamplesFolder()
      val njksExamples: Future[List[File]] = getNunjucksExamples.map(_.toList)

      for {
        _           <- dirCreation
        examples    <- njksExamples
        srcExample  <- examples
        playVersion <- PlayVersions.implementedPlayVersions
        destFile    <- allocateTwirlExamplePath(srcExample, playVersion)
        _ <- translateToTwirlExample(srcExample, destFile, playVersion)
      } yield ()
    }
  }

}