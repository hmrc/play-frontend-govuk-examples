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

class hmrcThereISAProblemWithTheServiceIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val linkToOtherService =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.linkToOtherService])

  @Inject private val linkToOtherServiceWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.linkToOtherServiceWelsh])

  @Inject private val noContact =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.noContact])

  @Inject private val noContactWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.noContactWelsh])

  @Inject private val problemWithService =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.problemWithService])

  @Inject private val problemWithServiceWelsh =
    Guice.createInjector().getInstance(classOf[html.examples.thereisaproblemwiththeservice.problemWithServiceWelsh])

  testRendering(HmrcFrontend, "there-is-a-problem-with-the-service", "linkToOtherService", linkToOtherService.f)
  testRendering(
    HmrcFrontend,
    "there-is-a-problem-with-the-service",
    "linkToOtherServiceWelsh",
    linkToOtherServiceWelsh.f)
  testRendering(HmrcFrontend, "there-is-a-problem-with-the-service", "noContact", noContact.f)
  testRendering(HmrcFrontend, "there-is-a-problem-with-the-service", "noContactWelsh", noContactWelsh.f)
  testRendering(HmrcFrontend, "there-is-a-problem-with-the-service", "problemWithService", problemWithService.f)
  testRendering(
    HmrcFrontend,
    "there-is-a-problem-with-the-service",
    "problemWithServiceWelsh",
    problemWithServiceWelsh.f)
}
