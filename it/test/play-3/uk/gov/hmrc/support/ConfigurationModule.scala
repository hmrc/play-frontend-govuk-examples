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

package uk.gov.hmrc.support

import com.google.inject.{Binder, Module}
import play.api.Configuration
import uk.gov.hmrc.hmrcfrontend.config.{TudorCrownConfig, RebrandConfig}

case class ConfigurationModule() extends Module {

  // As of version v5.13.0, govuk-frontend still has rebrand NOT enabled by default, so disabling in play-frontend-hmrc
  // is required for integration testing against the govuk-frontend examples
  val itTestConfiguration: Configuration = Configuration.from(Map(
    "play-frontend-hmrc.useRebrand" -> false
  ))

  override def configure(binder: Binder): Unit = {
    binder.bind(classOf[TudorCrownConfig]).toInstance(new TudorCrownConfig(itTestConfiguration))
    binder.bind(classOf[RebrandConfig]).toInstance(new RebrandConfig(itTestConfiguration))
  }
}
