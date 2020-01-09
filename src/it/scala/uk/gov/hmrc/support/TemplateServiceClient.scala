package uk.gov.hmrc.support

import org.scalatest.WordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.ws.{WSClient, WSResponse}

import uk.gov.hmrc.govukfrontend.examples.{ExampleType, GovukFrontend, HmrcFrontend}

import scala.concurrent.Future

trait TemplateServiceClient extends WordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  /**
    * Render a govuk-frontend and hmrc-frontend component using the template service
    *
    * @param componentName the govuk-frontend or hmrc-frontend component name
    * @param frontend the case object GovukFrontend or HmrcFrontend depending on which frontend the component derives from
    * @return [[WSResponse]] with the rendered component examples
    */
  def fetchExamples(componentName: String, frontend: ExampleType): Future[List[Example]] = {

    val frontendPath = frontend match {
      case GovukFrontend => "govuk"
      case HmrcFrontend => "hmrc"
    }

    val response: Future[WSResponse] = wsUrl(s"/examples-output/$frontendPath/$componentName").get()

    ???
  }
}
