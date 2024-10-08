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

class govukCharacterCountIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(GovukFrontend, "character-count", "default", charactercountDefault.f)
  testRendering(GovukFrontend, "character-count", "error", charactercountError.f)
  testRendering(GovukFrontend, "character-count", "withoutHeading", charactercountWithoutHeading.f)
  testRendering(GovukFrontend, "character-count", "wordCount", charactercountWordCount.f)

  // Excluded as the ordering of the attributes is different in Nunjucks and Twirl
  // testRendering(GovukFrontend, "character-count", "threshold", charactercountThreshold.f)
}
