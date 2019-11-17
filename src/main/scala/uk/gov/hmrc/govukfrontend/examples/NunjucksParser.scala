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

import fastparse.NoWhitespace._
import fastparse._
import play.api.libs.json.Reads
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.examples.AsJson._
import uk.gov.hmrc.govukfrontend.views.html.components._

case class NunjucksTemplate(imports: List[Import], body: List[NunjucksTemplateBody])
case class Import(from: String, macroName: String)

sealed trait NunjucksTemplateBody

case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody
case class TemplateHtml(content: Html) extends NunjucksTemplateBody
case class WhiteSpace(ws: String) extends NunjucksTemplateBody

object NunjucksParser {

  def nunjucksParser[_: P] = P(imports ~ ws ~ templateBody ~ End).map {
    case (imports, templateBody) => NunjucksTemplate(imports = imports.toList, body = templateBody.toList)
  }

  def ws[_: P] = P(CharsWhileIn(" \r\n", 0))

  def imports[_: P]: P[Seq[Import]] = P((importParser ~ ws).rep)

  def doubleQuotedString[_: P]: P[String] = P("\"" ~ (!"\"" ~ AnyChar).rep.! ~ "\"")

  def importParser[_: P]: P[Import] =
    P("{%" ~ ws ~ "from" ~ ws ~ doubleQuotedString ~ ws ~ "import" ~ ws ~ (!" " ~ AnyChar).rep.! ~ ws ~ "%}").map {
      case (importStatement, macroName) =>
        Import(from = importStatement, macroName = macroName)
    }

  def macroName[_: P] = P(("govuk" ~ (!"(" ~ AnyChar).rep).!)

  def macroCall[_: P]: P[MacroCall] =
    P(ws ~ "{{" ~ ws ~ macroName ~ "(" ~ ("{" ~ ws ~ (!"})" ~ AnyChar).rep ~ ws ~ "}").! ~ ")" ~ ws ~ "}}").map {
      case (m @ "govukAccordion", args) =>
        jsonToMacroCall[Accordion](m, args)
      case (m @ "govukBackLink", args) =>
        jsonToMacroCall[BackLink](m, args)
      case (m @ "govukBreadcrumbs", args) =>
        jsonToMacroCall[Breadcrumbs](m, args)
      case (m @ "govukButton", args) =>
        jsonToMacroCall[Button](m, args)
      case (m @ "govukCharacterCount", args) =>
        jsonToMacroCall[CharacterCount](m, args)
      case (m @ "govukCheckboxes", args) =>
        jsonToMacroCall[Checkboxes](m, args)
      case (m @ "govukDateInput", args) =>
        jsonToMacroCall[DateInput](m, args)
      case (m @ "govukDetails", args) =>
        jsonToMacroCall[Details](m, args)
      case (m @ "govukErrorMessage", args) =>
        jsonToMacroCall[ErrorMessage](m, args)
      case (m @ "govukErrorSummary", args) =>
        jsonToMacroCall[ErrorSummary](m, args)
      case (m @ "govukFieldset", args) =>
        jsonToMacroCall[Fieldset](m, args)
      case (m @ "govukFileUpload", args) =>
        jsonToMacroCall[FileUpload](m, args)
      case (m @ "govukFooter", args) =>
        jsonToMacroCall[Footer](m, args)
      case (m @ "govukHeader", args) =>
        jsonToMacroCall[Header](m, args)
      case (m @ "govukHint", args) =>
        jsonToMacroCall[Hint](m, args)
      case (m @ "govukInput", args) =>
        jsonToMacroCall[Input](m, args)
      case (m @ "govukInsetText", args) =>
        jsonToMacroCall[InsetText](m, args)
      case (m @ "govukLabel", args) =>
        jsonToMacroCall[Label](m, args)
      case (m @ "govukPanel", args) =>
        jsonToMacroCall[Panel](m, args)
      case (m @ "govukPhaseBanner", args) =>
        jsonToMacroCall[PhaseBanner](m, args)
      case (m @ "govukRadios", args) =>
        jsonToMacroCall[Radios](m, args)
      case (m @ "govukSelect", args) =>
        jsonToMacroCall[Select](m, args)
      case (m @ "govukSkipLink", args) =>
        jsonToMacroCall[SkipLink](m, args)
      case (m @ "govukSummaryList", args) =>
        jsonToMacroCall[SummaryList](m, args)
      case (m @ "govukTable", args) =>
        jsonToMacroCall[Table](m, args)
      case (m @ "govukTabs", args) =>
        jsonToMacroCall[Tabs](m, args)
      case (m @ "govukTag", args) =>
        jsonToMacroCall[Tag](m, args)
      case (m @ "govukTextarea", args) =>
        jsonToMacroCall[Textarea](m, args)
      case (m @ "govukWarningText", args) =>
        jsonToMacroCall[WarningText](m, args)
    }

  def jsonToMacroCall[T: Reads](macroName: String, args: String): MacroCall = {
    val templateParams = asJson(args).as[T]
    MacroCall(macroName = macroName, args = templateParams)
  }

  def html[_: P]: P[TemplateHtml] =
    P(ws ~ "<" ~ (!"{{" ~ AnyChar).rep.! ~ ws).map(html => TemplateHtml(Html(s"<$html")))

  def templateBody[_: P]: P[Seq[NunjucksTemplateBody]] =
    P((macroCall | html).rep(1))
}