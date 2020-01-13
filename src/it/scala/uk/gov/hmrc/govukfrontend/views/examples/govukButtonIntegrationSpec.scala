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

package uk.gov.hmrc.govukfrontend.views
package examples

import com.google.inject.Guice
import javax.inject.Inject
import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukButtonIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[html.examples.button.default])

  @Inject private val disabled =
    Guice.createInjector().getInstance(classOf[html.examples.button.disabled])

  @Inject private val preventDoubleClick =
    Guice.createInjector().getInstance(classOf[html.examples.button.preventDoubleClick])

  @Inject private val secondary =
    Guice.createInjector().getInstance(classOf[html.examples.button.secondary])

  @Inject private val secondaryCombo =
    Guice.createInjector().getInstance(classOf[html.examples.button.secondaryCombo])

  @Inject private val start =
    Guice.createInjector().getInstance(classOf[html.examples.button.start])

  @Inject private val warning =
    Guice.createInjector().getInstance(classOf[html.examples.button.warning])

  testRendering(GovukFrontend, "button", "default", default.f)
  testRendering(GovukFrontend, "button", "disabled", disabled.f)
  testRendering(GovukFrontend, "button", "preventDoubleClick", preventDoubleClick.f)
  testRendering(GovukFrontend, "button", "secondary", secondary.f)
  testRendering(GovukFrontend, "button", "secondaryCombo", secondaryCombo.f)
  testRendering(GovukFrontend, "button", "start", start.f)
  testRendering(GovukFrontend, "button", "warning", warning.f)
}
