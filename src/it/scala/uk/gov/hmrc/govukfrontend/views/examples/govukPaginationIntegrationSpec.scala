package uk.gov.hmrc.govukfrontend.views
package examples

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.examples.{GovukFrontend, MessagesSupport}
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukPaginationIntegrationSpec extends TemplateIntegrationSpec {

  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages

  testRendering(GovukFrontend, "pagination", "default", () => paginationDefault.render(messages, request))
  testRendering(GovukFrontend, "pagination", "ellipses", () => paginationEllipses.render(messages, request))
  testRendering(GovukFrontend, "pagination", "firstPage", () => paginationFirstPage.render(messages, request))
  testRendering(GovukFrontend, "pagination", "inPage", () => paginationInPage.render(messages, request))
  testRendering(GovukFrontend, "pagination", "lastPage", () => paginationLastPage.render(messages, request))
  testRendering(GovukFrontend, "pagination", "forListPages", () => paginationForListPages.render(messages, request))
  testRendering(
    GovukFrontend,
    "pagination",
    "forContentPages",
    () => paginationForContentPages.render(messages, request)
  )
}
