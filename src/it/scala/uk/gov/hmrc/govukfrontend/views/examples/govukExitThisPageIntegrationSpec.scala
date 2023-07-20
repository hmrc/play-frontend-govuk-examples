package uk.gov.hmrc.govukfrontend.views.examples

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.examples.{GovukFrontend, MessagesSupport}
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukExitThisPageIntegrationSpec extends TemplateIntegrationSpec {

  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages

  testRendering(GovukFrontend, "exit-this-page", "default", () => exitthispageDefault.render(messages, request))
  testRendering(GovukFrontend, "exit-this-page", "secondaryLink", exitthispageSecondaryLink.f)
}
