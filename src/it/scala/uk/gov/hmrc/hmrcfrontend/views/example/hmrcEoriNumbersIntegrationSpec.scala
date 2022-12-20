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

package uk.gov.hmrc.hmrcfrontend.views.example

import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcEoriNumbersIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(HmrcFrontend, "eori-numbers", "ask", eorinumbersAsk.f)
  testRendering(HmrcFrontend, "eori-numbers", "askWelsh", eorinumbersAskWelsh.f)
  testRendering(HmrcFrontend, "eori-numbers", "check", eorinumbersCheck.f)
  testRendering(HmrcFrontend, "eori-numbers", "checkWelsh", eorinumbersCheckWelsh.f)
  testRendering(HmrcFrontend, "eori-numbers", "error", eorinumbersError.f)
  testRendering(HmrcFrontend, "eori-numbers", "errorWelsh", eorinumbersErrorWelsh.f)
  testRendering(HmrcFrontend, "eori-numbers", "notValid", eorinumbersNotValid.f)
  testRendering(HmrcFrontend, "eori-numbers", "notValidWelsh", eorinumbersNotValidWelsh.f)
  testRendering(HmrcFrontend, "eori-numbers", "valid", eorinumbersValid.f)
  testRendering(HmrcFrontend, "eori-numbers", "validWelsh", eorinumbersValidWelsh.f)
}
