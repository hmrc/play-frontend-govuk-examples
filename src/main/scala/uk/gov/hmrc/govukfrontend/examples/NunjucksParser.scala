/*
 * Copyright 2020 HM Revenue & Customs
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
import uk.gov.hmrc.govukfrontend.views.html.components.{Header => GovukHeader, _}
import uk.gov.hmrc.hmrcfrontend.views.html.components._

object NunjucksParser {

  def nunjucksParser[_: P]: P[NunjucksTemplate] = P(comments ~ html.? ~ imports ~ ws ~ templateBody ~ End).map {
    case (html, imports, templateBody) =>
      NunjucksTemplate(prelim = html, imports = imports.toList, body = templateBody.toList)
  }

  def comments[_: P] = P("---" ~ ws ~ (!"---" ~ AnyChar).rep ~ "---" ~ ws).rep

  def ws[_: P] = P(CharsWhileIn(" \r\n", 0))

  def imports[_: P]: P[Seq[Import]] = P((importParser ~ ws).rep)

  def importParser[_: P]: P[Import] =
    P("{%" ~ "-".? ~ ws ~ "from" ~ ws ~ doubleQuotedString ~ ws ~ "import" ~ ws ~ (!" " ~ AnyChar).rep.! ~ ws ~ "-".? ~ "%}")
      .map {
        case (importStatement, macroName) =>
          Import(from = importStatement, macroName = macroName)
      }

  def doubleQuotedString[_: P]: P[String] = P("\"" ~ (!"\"" ~ AnyChar).rep.! ~ "\"")

  def templateBody[_: P]: P[Seq[NunjucksTemplateBody]] =
    P((macroCall() | callMacro | setBlock | html).rep(1))

  def html[_: P]: P[TemplateHtml] =
    P(ws ~ "<" ~ (!"{{" ~ !"{%" ~ AnyChar).rep.! ~ ws).map(html => TemplateHtml(Html(s"<$html")))

  def setBlock[_: P]: P[SetBlock] =
    P(blockName ~ ws ~ html.? ~ macroCall() ~ ws ~ "{% endset -%}" ~ ws).map {
      case (blockName, html, macroCall) =>
        SetBlock(blockName, html, macroCall)
    }

  def blockName[_: P]: P[String] = P("{% set" ~ ws ~ (!"%}" ~ CharIn("a-zA-Z")).rep.! ~ ws ~ "%}" ~ ws)

  def callMacro[_: P]: P[CallMacro] =
    P(macroCall("{% call", "%}") ~ ws ~ macroCall().rep ~ ws ~ "{% endcall %}").map {
      case (callMacro, macroCalls) => CallMacro(callMacro, macroCalls.toList)
    }

  def macroName[_: P]: P[String] = P(("govuk" ~ (!"(" ~ AnyChar).rep).! | ("hmrc" ~ (!"(" ~ AnyChar).rep).!)

  def macroCall[_: P](starting: String = "{{", terminating: String = "}}"): P[MacroCall] =
    P(ws ~ starting ~ ws ~ macroName ~ "(" ~ ("{" ~ ws ~ (!"})" ~ AnyChar).rep ~ ws ~ "}").! ~ ")" ~ ws ~ terminating ~ ws)
      .map {
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
          jsonToMacroCall[GovukHeader](m, args)
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
        case (m @ "hmrcPageHeading", args) =>
          jsonToMacroCall[PageHeading](m, args)
        case (m @ "hmrcNotificationBadge", args) =>
          jsonToMacroCall[NotificationBadge](m, args)
      }

  import scala.reflect.runtime.universe._

  def jsonToMacroCall[T: Reads: TypeTag](macroName: String, args: String): MacroCall = {
    val typeOfT = typeOf[T] match { case TypeRef(_, symbol, _) => symbol.toString.replace("type ", "").trim }

    var argsWithQuotes = args.lines.toStream
      .map { line =>
        if ((line matches """\s*html\s*:\s*([^"][A-z])+\s*$""") ||
            (typeOfT == "DateInput" && (line matches """\s*value\s*:\s*([^"][A-z0-9])+\s*$"""))) {
          val splits = line.split("\\s*:\\s*")
          splits(0) + ": \'" + splits(1) + "\'"
        } else line
      }
      .map { line =>
        if (line matches """\s*'[A-z"</>\s-=]+$""") {
          val splits = line.split("'")
          splits(0) + "MULTI_LINE_STRING>" + splits(1)
        } else line
      }
      .map { line =>
        if (line matches """\s*([^"'][A-z\s</>])+'\s*$""") {
          val splits = line.split("'")
          splits(0) + "<MULTI_LINE_STRING"
        } else line
      }
      .mkString("\n")

    if (argsWithQuotes contains "MULTI_LINE_STRING") {
      argsWithQuotes = argsWithQuotes
        .split("MULTI_LINE_STRING")
        .toStream
        .map { split =>
          if (split.startsWith(">") && split.endsWith("<")) {
            "'" + split.substring(1, split.length - 1).lines.toStream.mkString(" ") + "'"
          } else split
        }
        .mkString("\n")
    }

    val templateParams = asJson(argsWithQuotes).as[T]
    MacroCall(macroName = macroName, args = templateParams)
  }
}
