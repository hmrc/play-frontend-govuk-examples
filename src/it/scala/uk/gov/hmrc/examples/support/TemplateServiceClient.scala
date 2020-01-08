package uk.gov.hmrc.govukfrontend.support

import org.scalatest.WordSpecLike
import org.scalatestplus.play.PortNumber
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.OWrites
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

trait TemplateServiceClient extends WordSpecLike with WSScalaTestClient with GuiceOneAppPerSuite {

  implicit val portNumber: PortNumber = PortNumber(3000)

  implicit lazy val wsClient: WSClient = app.injector.instanceOf[WSClient]

  /**
    * Render a govuk-frontend component using the template service
    *
    * @param componentName the govuk-frontend component name as documented in the template service
    * @return [[WSResponse]] with the rendered component
    */
  def render(componentName: String): Future[WSResponse] =
    wsUrl(s"/examples-output/$componentName").get()
}
