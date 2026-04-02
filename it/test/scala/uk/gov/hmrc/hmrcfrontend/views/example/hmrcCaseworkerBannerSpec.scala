/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.hmrcfrontend.views
package example

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.examples.{HmrcFrontend, MessagesSupport}
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcCaseworkerBannerSpec extends TemplateIntegrationSpec {

  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages
  val welshMessages: Messages     = MessagesSupport().welshMessages

  testRendering(HmrcFrontend, "caseworker-guidance-banner", "dataEntry", () => caseworkerguidancebannerDataEntry.render(messages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "dataEntryWelsh", () => caseworkerguidancebannerDataEntryWelsh.render(welshMessages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "documentation", () => caseworkerguidancebannerDocumentation.render(messages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "documentationWelsh", () => caseworkerguidancebannerDocumentationWelsh.render(welshMessages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "multipleInstructions", () => caseworkerguidancebannerMultipleInstructions.render(messages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "multipleInstructionsWelsh", () => caseworkerguidancebannerMultipleInstructionsWelsh.render(welshMessages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "security", () => caseworkerguidancebannerSecurity.render(messages))
  testRendering(HmrcFrontend, "caseworker-guidance-banner", "securityWelsh", () => caseworkerguidancebannerSecurityWelsh.render(welshMessages))
}
