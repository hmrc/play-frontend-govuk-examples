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

package uk.gov.hmrc.hmrcfrontend.views.example

import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.hmrcfrontend.views.html.examples.matchanorganistionCompanyRegNumber
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcMatchAnOrganisationToHmrcRecordsIntegrationSpec extends TemplateIntegrationSpec {

  // TODO: At the moment, these tests are commented out as the examples cannot be correctly retrieved by the
  //  x-govuk-component-renderer, as the `-hmrc` in the componentName is stripped out in `getComponentIdentifier` in
  //  `utils.js`. A change will be needed in x-govuk-component-renderer to be able to run this test.
  // testRendering(HmrcFrontend, "match-an-organisation-to-hmrc-records", "company-reg-number", matchanorganistionCompanyRegNumber.f)
}
