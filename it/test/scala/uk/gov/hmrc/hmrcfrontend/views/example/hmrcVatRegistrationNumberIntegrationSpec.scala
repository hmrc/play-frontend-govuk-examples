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

package uk.gov.hmrc.hmrcfrontend.views
package example

import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcVatRegistrationNumberIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(HmrcFrontend, "vat-registration-number", "heading", vatregistrationnumberHeading.f)
  testRendering(HmrcFrontend, "vat-registration-number", "label", vatregistrationnumberLabel.f)
  testRendering(HmrcFrontend, "vat-registration-number", "labelError", vatregistrationnumberLabelError.f)
}
