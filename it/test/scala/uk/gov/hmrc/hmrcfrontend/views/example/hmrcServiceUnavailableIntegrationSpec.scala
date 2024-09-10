/*
 * Copyright 2023 HM Revenue & Customs
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

import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcServiceUnavailableIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(HmrcFrontend, "service-unavailable", "generalPage", serviceunavailableGeneralPage.f)
  testRendering(HmrcFrontend, "service-unavailable", "generalPageWelsh", serviceunavailableGeneralPageWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "linkToOtherService", serviceunavailableLinkToOtherService.f)
  testRendering(
    HmrcFrontend,
    "service-unavailable",
    "linkToOtherServiceWelsh",
    serviceunavailableLinkToOtherServiceWelsh.f
  )
  testRendering(HmrcFrontend, "service-unavailable", "serviceClosed", serviceunavailableServiceClosed.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceClosedWelsh", serviceunavailableServiceClosedWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceNotYetOpen", serviceunavailableServiceNotYetOpen.f)
  testRendering(
    HmrcFrontend,
    "service-unavailable",
    "serviceNotYetOpenWelsh",
    serviceunavailableServiceNotYetOpenWelsh.f
  )
  testRendering(HmrcFrontend, "service-unavailable", "serviceReplaced", serviceunavailableServiceReplaced.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceReplacedWelsh", serviceunavailableServiceReplacedWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceShutDown", serviceunavailableServiceShutDown.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceShutDownWelsh", serviceunavailableServiceShutDownWelsh.f)
  testRendering(
    HmrcFrontend,
    "service-unavailable",
    "serviceTemporarilyUnavailable",
    serviceunavailableServiceTemporarilyUnavailable.f
  )
  testRendering(
    HmrcFrontend,
    "service-unavailable",
    "serviceTemporarilyUnavailableWelsh",
    serviceunavailableServiceTemporarilyUnavailableWelsh.f
  )
}
