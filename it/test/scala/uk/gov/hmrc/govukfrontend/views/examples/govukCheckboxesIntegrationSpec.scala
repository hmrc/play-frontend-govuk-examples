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

class govukCheckboxesIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(GovukFrontend, "checkboxes", "default", checkboxesDefault.f)
  testRendering(GovukFrontend, "checkboxes", "conditionalReveal", checkboxesConditionalReveal.f)
  testRendering(GovukFrontend, "checkboxes", "error", checkboxesError.f)
  testRendering(GovukFrontend, "checkboxes", "hint", checkboxesHint.f)
  testRendering(GovukFrontend, "checkboxes", "small", checkboxesSmall.f)
  testRendering(GovukFrontend, "checkboxes", "withNoneOption", checkboxesWithNoneOption.f)
  testRendering(GovukFrontend, "checkboxes", "withNoneOptionInError", checkboxesWithNoneOptionInError.f)
  testRendering(GovukFrontend, "checkboxes", "withoutHeading", checkboxesWithoutHeading.f)
}
