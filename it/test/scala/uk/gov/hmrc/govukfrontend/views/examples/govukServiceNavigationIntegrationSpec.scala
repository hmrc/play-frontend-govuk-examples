/*
 * Copyright 2024 HM Revenue & Customs
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

package uk.gov.hmrc.govukfrontend.views.examples

import uk.gov.hmrc.govukfrontend.examples.{GovukFrontend, MessagesSupport}
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec
import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest


class govukServiceNavigationIntegrationSpec extends TemplateIntegrationSpec {
  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages

  testRendering(GovukFrontend, "service-navigation", "default", () => serviceNavigationDefault.render(messages, request))
  testRendering(GovukFrontend, "service-navigation", "withGovukHeader", () => serviceNavigationWithGovukHeader.render(messages, request))
  testRendering(GovukFrontend, "service-navigation", "withServiceName", () => serviceNavigationWithServiceName.render(messages, request))
  testRendering(GovukFrontend, "service-navigation", "withServiceNameAndNavigation", () => serviceNavigationWithServiceNameAndNavigation.render(messages, request))
}
