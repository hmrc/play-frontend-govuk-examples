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

package uk.gov.hmrc.govukfrontend.examples

import fastparse._
import NoWhitespace._
import play.twirl.api.Html

case class NunjucksTemplate(imports: List[Import], body: List[NunjucksTemplateBody])
case class Import(from: String, macroName: String)

sealed trait NunjucksTemplateBody

case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody
case class HtmlContent(content: Html) extends NunjucksTemplateBody
case class WhiteSpace(ws: String) extends NunjucksTemplateBody


object NunjucksParser {

//  def nunjucksParser[_: P] = P(imports ~ ws ~ templateBody)

  def ws[_: P] = P( (" " | "\n").rep(1) )

//  def imports[_: P] = P(importParser ~ ws).rep

  def doubleQuotedString[_: P] = P("\"" ~ (!"\"" ~ AnyChar).rep.! ~ "\"")

  def importParser[_: P] = P( "{%" ~ ws ~ "from" ~ ws ~ doubleQuotedString ~ ws ~ "import" ~ ws ~ (!" " ~ AnyChar).rep.! ~ ws ~ "%}").map {
    case (importStatement, macroName) => Import(from = importStatement, macroName = macroName)
  }

}
