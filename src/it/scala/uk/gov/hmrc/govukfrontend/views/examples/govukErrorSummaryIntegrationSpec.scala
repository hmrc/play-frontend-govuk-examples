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
import org.scalatest.Ignore
import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

@Ignore
class govukErrorSummaryIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.errorsummary.default])

  @Inject private val linking =
    Guice.createInjector().getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.errorsummary.linking])

  @Inject private val linkingCheckboxesRadios =
    Guice
      .createInjector()
      .getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.errorsummary.linkingCheckboxesRadios])

  @Inject private val linkingMultipleFields =
    Guice
      .createInjector()
      .getInstance(classOf[uk.gov.hmrc.govukfrontend.views.html.examples.errorsummary.linkingMultipleFields])

  testRendering(GovukFrontend, "error-summary", "default", default.f)
  testRendering(GovukFrontend, "error-summary", "linking", linking.f)
  testRendering(GovukFrontend, "error-summary", "linkingCheckboxesRadios", linkingCheckboxesRadios.f)
  testRendering(GovukFrontend, "error-summary", "linkingMultipleFields", linkingMultipleFields.f)
}
