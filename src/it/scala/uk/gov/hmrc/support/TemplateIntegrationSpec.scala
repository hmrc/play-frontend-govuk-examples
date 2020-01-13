package uk.gov.hmrc.support

import org.jsoup.Jsoup
import org.scalacheck.ShrinkLowPriority
import org.scalatest.WordSpec
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import play.twirl.api.HtmlFormat
import uk.gov.hmrc.govukfrontend.examples.ExampleType
import uk.gov.hmrc.govukfrontend.views.PreProcessor
import uk.gov.hmrc.govukfrontend.views.TemplateDiff._

import scala.util.{Failure, Success, Try}

/**
  * Base class for integration testing a Twirl template against the Nunjucks template rendering service
  *
  */
abstract class TemplateIntegrationSpec
    extends WordSpec
    with TemplateServiceClient
    with PreProcessor
    with ShrinkLowPriority
    with JsoupHelpers
    with ScalaFutures
    with IntegrationPatience {

  def testRendering(
    frontend: ExampleType,
    componentName: String,
    exampleName: String,
    twirlTemplate: () => HtmlFormat.Appendable) {

    s"$componentName example $exampleName" should {

      "compile to the same HTML as the corresponding Nunjucks" in {

        val examples: List[Example] = fetchExamples(componentName, frontend).futureValue

        val nunjucks: Example = examples
          .find(_.exampleName.replaceAll("-", "").matches(exampleName.toLowerCase()))
          .getOrElse(throw new Exception(s"Nunjucks example not found for Twirl example $exampleName"))

        matchTwirlWithNunjucks(twirlRendering, nunjucks.html)
      }
    }

    def twirlRendering =
      Try(twirlTemplate.apply())
        .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))

    def matchTwirlWithNunjucks(twirlOutputHtml: Try[String], nunJucksOutputHtml: String) =
      twirlOutputHtml match {

        case Success(twirlOutputHtml) =>
          val preProcessedTwirlHtml    = preProcess(twirlOutputHtml)
          val preProcessedNunjucksHtml = preProcess(nunJucksOutputHtml)
          val prop                     = preProcessedTwirlHtml == preProcessedNunjucksHtml

          if (!prop) {
            reportDiff(
              preProcessedTwirlHtml    = preProcessedTwirlHtml,
              preProcessedNunjucksHtml = preProcessedNunjucksHtml
            )
          }

          prop
        case Failure(TemplateValidationException(message)) =>
          println(s"Failed to validate the parameters for the $componentName template")
          println(s"Exception: $message")
          println("Skipping property evaluation")

          false
      }

    def reportDiff(preProcessedTwirlHtml: String, preProcessedNunjucksHtml: String): Unit = {

      val diffPath =
        templateDiffPath(
          twirlOutputHtml    = preProcessedTwirlHtml,
          nunJucksOutputHtml = preProcessedNunjucksHtml,
          diffFilePrefix     = Some(exampleName)
        )

      println(s"Diff between Twirl and Nunjucks outputs (please open diff HTML file in a browser): file://$diffPath\n")

      println("-" * 80)
      println("Twirl")
      println("-" * 80)

      val formattedTwirlHtml = Jsoup.parseBodyFragment(preProcessedTwirlHtml).body.html
      println(s"\nTwirl output:\n$formattedTwirlHtml\n")

      println("-" * 80)
      println("Nunjucks")
      println("-" * 80)

      val formattedNunjucksHtml = Jsoup.parseBodyFragment(preProcessedNunjucksHtml).body.html
      println(s"\nNunjucks output:\n$formattedNunjucksHtml\n")

    }
  }
}
