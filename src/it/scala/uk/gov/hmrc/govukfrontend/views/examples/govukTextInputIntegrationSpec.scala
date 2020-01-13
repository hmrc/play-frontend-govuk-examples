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

class govukTextInputIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[html.examples.textinput.default])

  @Inject private val error =
    Guice.createInjector().getInstance(classOf[html.examples.textinput.error])

  @Inject private val inputAutocompleteAttribute =
    Guice
      .createInjector()
      .getInstance(classOf[html.examples.textinput.inputAutocompleteAttribute])

  @Inject private val inputFixedWidth =
    Guice.createInjector().getInstance(classOf[html.examples.textinput.inputFixedWidth])

  @Inject private val inputFluidWidth =
    Guice.createInjector().getInstance(classOf[html.examples.textinput.inputFluidWidth])

  @Inject private val inputHintText =
    Guice.createInjector().getInstance(classOf[html.examples.textinput.inputHintText])

  @Inject private val inputSpellcheckDisabled =
    Guice
      .createInjector()
      .getInstance(classOf[html.examples.textinput.inputSpellcheckDisabled])

  testRendering(GovukFrontend, "text-input", "default", default.f)
  testRendering(GovukFrontend, "text-input", "error", error.f)
  testRendering(GovukFrontend, "text-input", "inputAutocompleteAttribute", inputAutocompleteAttribute.f)
  testRendering(GovukFrontend, "text-input", "inputFixedWidth", inputFixedWidth.f)
  testRendering(GovukFrontend, "text-input", "inputFluidWidth", inputFluidWidth.f)
  testRendering(GovukFrontend, "text-input", "inputHintText", inputHintText.f)
  testRendering(GovukFrontend, "text-input", "inputSpellcheckDisabled", inputSpellcheckDisabled.f)
}
