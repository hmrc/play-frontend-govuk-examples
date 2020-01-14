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

package uk.gov.hmrc.hmrcfrontend.views
package example

import com.google.inject.Guice
import javax.inject.Inject
import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcPageHeadingIntegrationSpec extends TemplateIntegrationSpec {

  @Inject private val withoutSubheading =
    Guice.createInjector().getInstance(classOf[html.examples.pageheading.withoutSubheading])
  @Inject private val withSubheading =
    Guice.createInjector().getInstance(classOf[html.examples.pageheading.withSubheading])

  testRendering(HmrcFrontend, "page-heading", "withoutSubheading", withoutSubheading.f)
  testRendering(HmrcFrontend, "page-heading", "withSubheading", withSubheading.f)
}
