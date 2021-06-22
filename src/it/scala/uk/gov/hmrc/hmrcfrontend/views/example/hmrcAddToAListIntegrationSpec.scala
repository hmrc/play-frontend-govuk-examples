package uk.gov.hmrc.hmrcfrontend.views
package example

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.examples.{HmrcFrontend, MessagesSupport}
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcAddToAListIntegrationSpec extends TemplateIntegrationSpec {

  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages

  testRendering(HmrcFrontend, "add-to-a-list", "addToSummary", () => addtoalistAddToSummary.render(messages, request))
  testRendering(HmrcFrontend, "add-to-a-list", "changeList", addtoalistChangeList.f)
  testRendering(HmrcFrontend, "add-to-a-list", "checkAnswers", addtoalistCheckAnswers.f)
  testRendering(HmrcFrontend, "add-to-a-list", "removeFromList", addtoalistRemoveFromList.f)
  testRendering(HmrcFrontend, "add-to-a-list", "viewASummary", () => addtoalistViewASummary.render(messages, request))
}
