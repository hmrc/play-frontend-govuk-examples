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

package uk.gov.hmrc.govukfrontend.examples

import javax.script.{ScriptEngine, ScriptEngineManager}
import play.api.libs.json.{JsValue, Json}

object AsJson {

  lazy val engine: ScriptEngine =
    new ScriptEngineManager(getClass.getClassLoader).getEngineByMimeType("text/javascript")

  /**
    * Converts Javascript like arguments to Nunjucks templates to Json.
    *
    * @param jsonStr
    * @return
    */
  def asJson(jsonStr: String): JsValue = {
    val json = engine.eval(s"""JSON.stringify($jsonStr)""")
    Json.parse(json.toString)
  }
}
