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
package examples

import com.google.inject.Guice
import javax.inject.Inject
import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcAccountOfficeReferenceIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val heading =
    Guice.createInjector().getInstance(classOf[html.examples.accountsofficereference.heading])

  @Inject private val label =
    Guice.createInjector().getInstance(classOf[html.examples.accountsofficereference.label])

  @Inject private val labelError =
    Guice.createInjector().getInstance(classOf[html.examples.accountsofficereference.labelError])

  testRendering(HmrcFrontend, "accounts-office-reference", "heading", heading.f)
  testRendering(HmrcFrontend, "accounts-office-reference", "label", label.f)
  testRendering(HmrcFrontend, "accounts-office-reference", "labelError", labelError.f)
}
