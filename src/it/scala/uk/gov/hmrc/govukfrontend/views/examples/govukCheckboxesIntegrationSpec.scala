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

package uk.gov.hmrc.govukfrontend.views.examples

import com.google.inject.Guice
import javax.inject.Inject
import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukCheckboxesIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.checkboxes.default])

  @Inject private val conditionalReveal =
    Guice
      .createInjector()
      .getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.checkboxes.conditionalReveal])

  @Inject private val error =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.checkboxes.error])

  @Inject private val hint =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.checkboxes.hint])

  @Inject private val small =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.checkboxes.small])

  testRendering(GovukFrontend, "checkboxes", "default", default.f)
  testRendering(GovukFrontend, "checkboxes", "conditionalReveal", conditionalReveal.f)
  testRendering(GovukFrontend, "checkboxes", "error", error.f)
  testRendering(GovukFrontend, "checkboxes", "hint", hint.f)
  testRendering(GovukFrontend, "checkboxes", "small", small.f)
}
