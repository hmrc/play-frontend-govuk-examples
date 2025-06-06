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

package uk.gov.hmrc.govukfrontend.views
package examples

import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukButtonIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(GovukFrontend, "button", "buttonGroup", buttonButtonGroup.f)
  testRendering(GovukFrontend, "button", "default", buttonDefault.f)
  testRendering(GovukFrontend, "button", "disabled", buttonDisabled.f)
  testRendering(GovukFrontend, "button", "preventDoubleClick", buttonPreventDoubleClick.f)
  testRendering(GovukFrontend, "button", "secondary", buttonSecondary.f)
  testRendering(GovukFrontend, "button", "secondaryCombo", buttonSecondaryCombo.f)
  testRendering(GovukFrontend, "button", "start", buttonStart.f)
  testRendering(GovukFrontend, "button", "warning", buttonWarning.f)
}
