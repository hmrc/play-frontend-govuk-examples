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

import fastparse.Parsed
import org.scalatest.{Matchers, WordSpec}

class TwirlFormatterSpec extends WordSpec with Matchers {

  "Formatter" should {

    "format GovukBackLink " in {

      val gouvukBackLinkNunjucks =
        """{% from "govuk/components/back-link/macro.njk" import govukBackLink %}
          |
          |{{ govukBackLink({
          |  text: "Back",
          |  href: "#"
          |}) }}""".stripMargin

      val govukBackLinkTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this()
          |
          |@()
          |@GovukBackLink(BackLink(href = "#", content = Text(value = "Back")))""".stripMargin

      val gouvukBackLinkParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukBackLinkNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukBackLinkParsed.get.value) shouldBe govukBackLinkTwirlExpected
    }

    "format GovukButton " in {

      val gouvukButtonNunjucks = """{% from "govuk/components/button/macro.njk" import govukButton %}
                                   |
                                   |{{ govukButton({
                                   |text: "Save and continue"
                                   |}) }}""".stripMargin

      val govukButtonTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this()
          |
          |@()
          |@GovukButton(Button(content = Text(value = "Save and continue")))""".stripMargin

      val gouvukButtonParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukButtonNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukButtonParsed.get.value) shouldBe govukButtonTwirlExpected
    }

    "format GovukErrorSummary " ignore {

      val gouvukErrorSummaryNunjucks = """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}
                                         |
                                         |{{ govukErrorSummary({
                                         |  titleText: "There is a problem",
                                         |  errorList: [
                                         |    {
                                         |      text: "The date your passport was issued must be in the past",
                                         |      href: "#passport-issued-error"
                                         |    },
                                         |    {
                                         |      text: "Enter a postcode, like AA1 1AA",
                                         |      href: "#postcode-error"
                                         |    }
                                         |  ]
                                         |}) }}""".stripMargin

      val govukErrorSummaryTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this()
          |
          |@()
          |@GovukErrorSummary(ErrorSummary(
          |  errorList =
          |    Seq(
          |      ErrorLink(
          |        href = Some("#passport-issued-error"),
          |        content = Text(value = "The date your passport was issued must be in the past")
          |      ),
          |      ErrorLink(
          |        href = Some("#postcode-error"),
          |        content = Text(value = "Enter a postcode, like AA1 1AA")
          |      )
          |    ),
          |  title = Text(value = "There is a problem")
          |))""".stripMargin

      val gouvukErrorSummaryParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukErrorSummaryNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukErrorSummaryParsed.get.value) shouldBe govukErrorSummaryTwirlExpected
    }

  }

}
