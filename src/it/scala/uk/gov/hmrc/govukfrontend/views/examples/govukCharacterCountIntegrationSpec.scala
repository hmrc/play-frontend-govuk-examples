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

class govukCharacterCountIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val default =
    Guice.createInjector().getInstance(classOf[html.examples.charactercount.default])

  @Inject private val error =
    Guice.createInjector().getInstance(classOf[html.examples.charactercount.error])

  @Inject private val labelPageHeading =
    Guice
      .createInjector()
      .getInstance(classOf[html.examples.charactercount.labelPageHeading])

  @Inject private val threshold =
    Guice.createInjector().getInstance(classOf[html.examples.charactercount.threshold])

  @Inject private val wordCount =
    Guice.createInjector().getInstance(classOf[html.examples.charactercount.wordCount])

  testRendering(GovukFrontend, "character-count", "default", default.f)
  testRendering(GovukFrontend, "character-count", "error", error.f)
  testRendering(GovukFrontend, "character-count", "labelPageHeading", labelPageHeading.f)
  testRendering(GovukFrontend, "character-count", "threshold", threshold.f)
  testRendering(GovukFrontend, "character-count", "wordCount", wordCount.f)
}
