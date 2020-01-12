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

class govukRadiosIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.default])

  @Inject private val conditionalReveal =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.conditionalReveal])

  @Inject private val divider =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.divider])

  @Inject private val error =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.error])

  @Inject private val hint =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.hint])

  @Inject private val small =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.small])

  @Inject private val stacked =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.radios.stacked])

  testRendering(GovukFrontend, "radios", "default", default.f)
  testRendering(GovukFrontend, "radios", "conditionalReveal", conditionalReveal.f)
  testRendering(GovukFrontend, "radios", "divider", divider.f)
  testRendering(GovukFrontend, "radios", "error", error.f)
  testRendering(GovukFrontend, "radios", "hint", hint.f)
  testRendering(GovukFrontend, "radios", "small", small.f)
  testRendering(GovukFrontend, "radios", "stacked", stacked.f)
}
