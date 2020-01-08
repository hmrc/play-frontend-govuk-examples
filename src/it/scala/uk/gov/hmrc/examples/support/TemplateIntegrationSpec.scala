package uk.gov.hmrc.examples.support

import org.jsoup.Jsoup
import org.scalacheck.ShrinkLowPriority
import org.scalatest.WordSpec
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import uk.gov.hmrc.govukfrontend.support.Implicits._
import uk.gov.hmrc.govukfrontend.support.TemplateServiceClient
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
//    with TwirlRenderer[T]
    with ShrinkLowPriority
    with JsoupHelpers
    with ScalaFutures
    with IntegrationPatience {

  def testRendering(componentName: String, exampleName: String, example: play.twirl.api.HtmlFormat.Appendable) {

    val response = render(componentName)

    val nunJucksOutputHtml = response.futureValue.bodyAsString

    val tryRenderTwirl =
      Try(example)
        .transform(html => Success(html.body), f => Failure(new TemplateValidationException(f.getMessage)))

    tryRenderTwirl match {

      case Success(twirlOutputHtml) =>
        val preProcessedTwirlHtml    = preProcess(twirlOutputHtml)
        val preProcessedNunjucksHtml = preProcess(nunJucksOutputHtml)
        val prop                     = preProcessedTwirlHtml == preProcessedNunjucksHtml

        if (!prop) {
          reportDiff(
            exampleName,
            preProcessedTwirlHtml    = preProcessedTwirlHtml,
            preProcessedNunjucksHtml = preProcessedNunjucksHtml
          )
        }

        prop
      case Failure(TemplateValidationException(message)) =>
        println(s"Failed to validate the parameters for the $componentName template")
        println(s"Exception: $message")
        println("Skipping property evaluation")

        true
    }
  }

  def reportDiff(exampleName: String, preProcessedTwirlHtml: String, preProcessedNunjucksHtml: String): Unit = {

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
