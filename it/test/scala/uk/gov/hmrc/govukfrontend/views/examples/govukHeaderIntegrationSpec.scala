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

import play.twirl.api.{Html, HtmlFormat}
import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukHeaderIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(GovukFrontend, "header", "default", withOverriddenAssetPath(headerDefault.f))
  testRendering(GovukFrontend, "header", "withServiceName", withOverriddenAssetPath(headerWithServiceName.f))
  testRendering(
    GovukFrontend,
    "header",
    "withServiceNameAndNavigation",
    withOverriddenAssetPath(headerWithServiceNameAndNavigation.f)
  )

  private def withOverriddenAssetPath(render: () => HtmlFormat.Appendable): () => HtmlFormat.Appendable = { () =>
    Html(render().toString().replace("/assets/govuk/images/", "/assets/images/"))
  }
}
