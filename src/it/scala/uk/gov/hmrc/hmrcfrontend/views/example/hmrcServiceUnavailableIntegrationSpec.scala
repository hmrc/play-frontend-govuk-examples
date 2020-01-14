/*
 * Copyright 2019 HM Revenue & Customs
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

import com.google.inject.Guice
import javax.inject.Inject
import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcServiceUnavailableIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val generalPage =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.generalPage])

  @Inject private val generalPageWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.generalPageWelsh])

  @Inject private val linkToOtherService =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.linkToOtherService])

  @Inject private val linkToOtherServiceWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.linkToOtherServiceWelsh])

  @Inject private val serviceClosed =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceClosed])

  @Inject private val serviceClosedWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceClosedWelsh])

  @Inject private val serviceNotYetOpen =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceNotYetOpen])

  @Inject private val serviceNotYetOpenWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceNotYetOpenWelsh])

  @Inject private val serviceReplaced =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceReplaced])

  @Inject private val serviceReplacedWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceReplacedWelsh])

  @Inject private val serviceShutDown =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceShutDown])

  @Inject private val serviceShutDownWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceShutDownWelsh])

  @Inject private val serviceTemporarilyUnavailable =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceTemporarilyUnavailable])

  @Inject private val serviceTemporarilyUnavailableWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.serviceunavailable.serviceTemporarilyUnavailableWelsh])

  testRendering(HmrcFrontend, "service-unavailable", "generalPage", generalPage.f)
  testRendering(HmrcFrontend, "service-unavailable", "generalPageWelsh", generalPageWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "linkToOtherService", linkToOtherService.f)
  testRendering(HmrcFrontend, "service-unavailable", "linkToOtherServiceWelsh", linkToOtherServiceWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceClosed", serviceClosed.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceClosedWelsh", serviceClosedWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceNotYetOpen", serviceNotYetOpen.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceNotYetOpenWelsh", serviceNotYetOpenWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceReplaced", serviceReplaced.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceReplacedWelsh", serviceReplacedWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceShutDown", serviceShutDown.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceShutDownWelsh", serviceShutDownWelsh.f)
  testRendering(HmrcFrontend, "service-unavailable", "serviceTemporarilyUnavailable", serviceTemporarilyUnavailable.f)
  testRendering(
    HmrcFrontend,
    "service-unavailable",
    "serviceTemporarilyUnavailableWelsh",
    serviceTemporarilyUnavailableWelsh.f)
}
