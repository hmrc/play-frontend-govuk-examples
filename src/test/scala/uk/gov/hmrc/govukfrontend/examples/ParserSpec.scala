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

import fastparse.Parsed.Success
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsObject, JsValue}
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.html.components._
import NunjucksParser._
import fastparse.Parsed

class ParserSpec extends WordSpec with Matchers {

//  "A parser for a Nunjucks template" should {
//    "parse successfully" in {
//
//      val nunjucks =
//        """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}
//          |
//          |{{ govukErrorSummary({
//          |  titleText: "There is a problem",
//          |  errorList: [
//          |    {
//          |      text: "The date your passport was issued must be in the past",
//          |      href: "#passport-issued-error"
//          |    },
//          |    {
//          |      text: "Enter a postcode, like AA1 1AA",
//          |      href: "#postcode-error"
//          |    }
//          |  ]
//          |}) }}""".stripMargin
//
//      fastparse.parse(nunjucks, nunjucksParser(_)) shouldBe Success(
//        NunjucksTemplate(
//          imports = List(
//        Import(from = "govuk/components/error-summary/macro.njk", macroName = "govukErrorSummary")
//          ),
//          body = List(MacroCall(
//            macroName = "govukErrorSummary",
//            args = ErrorSummary(
//              title = Text("There is a problem"),
//              errorList = Seq(
//                ErrorLink(
//                  content = Text("The date your passport was issued must be in the past"),
//                  href = "#passport-issued-error"
//                ),
//                ErrorLink(
//                  content = Text("Enter a postcode, like AA1 1AA"),
//                  href = "#postcode-error"
//                )
//              )
//            )
//          ))
//        )
//      )
//
//    }
//  }

  "parse whitespace" should {
    "parse single line whitespace" in {
      val wsString = "   "

      fastparse.parse(wsString, ws(_)) shouldBe Success((), 3)
    }

    "parse multi-line whitespace" in {
      val wsString = "   \n "

      fastparse.parse(wsString, ws(_)) shouldBe Success((), 5)
    }
  }

  "doubleQuotedString parser" should {
    "parse" in {
      val s = "\"abc\""

      fastparse.parse(s, doubleQuotedString(_)) shouldBe Success(
        "abc", 5
      )
    }
  }

  "import parser" should {
    "parse" in {
      val s = """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}"""

      val Parsed.Success(parsedValue, _) = fastparse.parse(s, importParser(_))

       parsedValue shouldBe
        Import("govuk/components/error-summary/macro.njk", "govukErrorSummary")
    }
  }
}
