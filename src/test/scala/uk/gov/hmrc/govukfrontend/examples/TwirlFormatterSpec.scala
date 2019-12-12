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

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.govukfrontend.examples.TwirlFormatter._

class TwirlFormatterSpec extends WordSpec with Matchers {

  implicit class StringUtil(string: String) {

    def trimSpaces = string.replaceAll("""\s+""", "")

    val print: Unit = println(string)
  }

  val htmlAddress      = "HtmlContent(\"\"\"72 Guild Street<br>London<br>SE23 6FH\"\"\")"
  val htmlContentBegin = "HtmlContent(\"\"\""
  val htmlContentEnd   = "\"\"\")"

  "Play 2.5 formatter" should {

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
          |@()
          |
          |@GovukBackLink(BackLink(href = "#", content = Text("Back")))""".stripMargin

      val gouvukBackLinkParsed = fastparse.parse(gouvukBackLinkNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukBackLinkTwirl  = formatPlay25(gouvukBackLinkParsed.get.value)
      gouvukBackLinkTwirl.print
      gouvukBackLinkTwirl.trimSpaces shouldBe govukBackLinkTwirlExpected.trimSpaces
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
          |@()
          |
          |@GovukButton(Button(content = Text("Save and continue")))""".stripMargin

      val gouvukButtonParsed = fastparse.parse(gouvukButtonNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukButtonTwirl  = formatPlay25(gouvukButtonParsed.get.value)
      gouvukButtonTwirl.print
      gouvukButtonTwirl.trimSpaces shouldBe govukButtonTwirlExpected.trimSpaces
    }

    "format GovukErrorSummary " in {

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
          |@()
          |
          |@GovukErrorSummary(ErrorSummary(
          |  errorList =
          |    Seq(
          |      ErrorLink(
          |        href = Some("#passport-issued-error"),
          |        content = Text("The date your passport was issued must be in the past")
          |      ),
          |      ErrorLink(
          |        href = Some("#postcode-error"),
          |        content = Text("Enter a postcode, like AA1 1AA")
          |      )
          |    ),
          |  title = Text("There is a problem")
          |))""".stripMargin

      val gouvukErrorSummaryParsed = fastparse.parse(gouvukErrorSummaryNunjucks, NunjucksParser.nunjucksParser(_))
      val govukErrorSummaryTwirl   = formatPlay25(gouvukErrorSummaryParsed.get.value)
      govukErrorSummaryTwirl.print
      govukErrorSummaryTwirl.trimSpaces shouldBe govukErrorSummaryTwirlExpected.trimSpaces
    }

    "format GovukFieldset " in {

      val gouvukFieldsetNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |{% from "govuk/components/fieldset/macro.njk" import govukFieldset %}
          |
          |{% call govukFieldset({
          |  legend: {
          |    text: "What is your address?",
          |    classes: "govuk-fieldset__legend--xl",
          |    isPageHeading: true
          |  }
          |}) %}
          |
          |  {{ govukInput({
          |    label: {
          |      html: 'Building and street <span class="govuk-visually-hidden">line 1 of 2</span>'
          |    },
          |    id: "address-line-1",
          |    name: "address-line-1"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      html: '<span class="govuk-visually-hidden">Building and street line 2 of 2</span>'
          |    },
          |    id: "address-line-2",
          |    name: "address-line-2"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "Town or city"
          |    },
          |    classes: "govuk-!-width-two-thirds",
          |    id: "address-town",
          |    name: "address-town"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "County"
          |    },
          |    classes: "govuk-!-width-two-thirds",
          |    id: "address-county",
          |    name: "address-county"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "Postcode"
          |    },
          |    classes: "govuk-input--width-10",
          |    id: "address-postcode",
          |    name: "address-postcode"
          |  }) }}
          |
          |{% endcall %}""".stripMargin

      val govukFieldsetTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukFieldset(Fieldset(
          |  legend =
          |    Some(
          |      Legend(
          |        content = Text( "What is your address?"),
          |        classes = "govuk-fieldset__legend--xl",
          |        isPageHeading = true
          |      )),
          |  html = html
          |))
          |
          |@html = {
          |  @GovukInput(Input(
          |    id = "address-line-1",
          |    name = "address-line-1",
          |    label = Label(content = $htmlContentBegin Building and street <span class="govuk-visually-hidden">line 1 of 2</span>$htmlContentEnd)
          |  ))
          |  @GovukInput(Input(
          |    id = "address-line-2",
          |    name = "address-line-2",
          |    label = Label(content = $htmlContentBegin <span class="govuk-visually-hidden">Building and street line 2 of 2</span>$htmlContentEnd)
          |  ))
          |  @GovukInput(Input(
          |    id = "address-town",
          |    name = "address-town",
          |    label = Label(content = Text( "Town or city")),
          |    classes = "govuk-!-width-two-thirds"
          |  ))
          |  @GovukInput(Input(
          |    id = "address-county",
          |    name = "address-county",
          |    label = Label(content = Text( "County")),
          |    classes = "govuk-!-width-two-thirds"
          |  ))
          |  @GovukInput(Input(
          |    id = "address-postcode",
          |    name = "address-postcode",
          |    label = Label(content = Text( "Postcode")),
          |    classes = "govuk-input--width-10"
          |  ))
          |}""".stripMargin

      val gouvukFieldsetParsed = fastparse.parse(gouvukFieldsetNunjucks, NunjucksParser.nunjucksParser(_))
      val govukFieldsetTwirl   = formatPlay25(gouvukFieldsetParsed.get.value)
      govukFieldsetTwirl.print
      govukFieldsetTwirl.trimSpaces shouldBe govukFieldsetTwirlExpected.trimSpaces
    }

    "format GovukFooter " in {

      val gouvukFooterNunjucks =
        """{% from "govuk/components/footer/macro.njk" import govukFooter %}
          |
          |{{ govukFooter({}) }}""".stripMargin

      val govukFooterTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukFooter()""".stripMargin

      val gouvukFooterParsed = fastparse.parse(gouvukFooterNunjucks, NunjucksParser.nunjucksParser(_))
      val govukFooterTwirl   = formatPlay25(gouvukFooterParsed.get.value)
      govukFooterTwirl.print
      govukFooterTwirl.trimSpaces shouldBe govukFooterTwirlExpected.trimSpaces
    }

    "format GovukHeader " in {

      val gouvukHeaderNunjucks =
        """{% from "govuk/components/header/macro.njk" import govukHeader %}
          |
          |{{ govukHeader({
          |  homepageUrl: "#"
          |}) }}""".stripMargin

      val govukHeaderTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukHeader(Header(
          |  homepageUrl = Some("#")
          |))""".stripMargin

      val gouvukHeaderParsed = fastparse.parse(gouvukHeaderNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukHeaderTwirl  = formatPlay25(gouvukHeaderParsed.get.value)
      gouvukHeaderTwirl.print
      gouvukHeaderTwirl.trimSpaces shouldBe govukHeaderTwirlExpected.trimSpaces
    }

    "format GovukPanel " in {

      val gouvukPanelNunjucks =
        """{% from "govuk/components/panel/macro.njk" import govukPanel %}
          |
          |{{ govukPanel({
          |  titleText: "Application complete",
          |  html: "Your reference number<br><strong>HDJ2123F</strong>"
          |}) }}""".stripMargin

      val htmlContent = "\"\"\"Your reference number<br><strong>HDJ2123F</strong>\"\"\""
      val govukPanelTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukPanel(Panel(
          |  title = Text( "Application complete"),
          |  content = HtmlContent($htmlContent)
          |))""".stripMargin

      val gouvukPanelParsed = fastparse.parse(gouvukPanelNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukPanelTwirl  = formatPlay25(gouvukPanelParsed.get.value)
      gouvukPanelTwirl.print
      gouvukPanelTwirl.trimSpaces shouldBe govukPanelTwirlExpected.trimSpaces
    }

    "format GovukRadios " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text( "Yes"), value = Some("yes")),
          |    RadioItem(content = Text( "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios1 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "where-do-you-live",
          |  name: "where-do-you-live",
          |  fieldset: {
          |    legend: {
          |      text: "Where do you live?",
          |      classes: "govuk-fieldset__legend--xl",
          |      isPageHeading: true
          |    }
          |  },
          |  items: [
          |    {
          |      value: "england",
          |      text: "England"
          |    },
          |    {
          |      value: "scotland",
          |      text: "Scotland"
          |    },
          |    {
          |      value: "wales",
          |      text: "Wales"
          |    },
          |    {
          |      value: "northern-ireland",
          |      text: "Northern Ireland"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text( "England"), value = Some("england")),
          |    RadioItem(content = Text( "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text( "Wales"), value = Some("wales")),
          |    RadioItem(content = Text( "Northern Ireland"), value = Some("northern-ireland"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios2 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text( "Yes"), value = Some("yes")),
          |    RadioItem(content = Text( "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadios       = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadios.print
      gouvukRadios.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios3 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "sign-in",
          |  name: "sign-in",
          |  fieldset: {
          |    legend: {
          |      text: "How do you want to sign in?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "You’ll need an account to prove your identity and complete your Self Assessment."
          |  },
          |  items: [
          |    {
          |      value: "government-gateway",
          |      text: "Sign in with Government Gateway",
          |      label: {
          |        classes: "govuk-label--s"
          |      },
          |      hint: {
          |        text: "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before."
          |      }
          |    },
          |    {
          |      value: "govuk-verify",
          |      text: "Sign in with GOV.UK Verify",
          |      label: {
          |        classes: "govuk-label--s"
          |      },
          |      hint: {
          |        text: "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity."
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "How do you want to sign in?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "You’ll need an account to prove your identity and complete your Self Assessment.")
          |  )),
          |  idPrefix = Some("sign-in"),
          |  name = "sign-in",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Sign in with Government Gateway"),
          |      value = Some("government-gateway"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text( "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before.")
          |      ))
          |    ),
          |    RadioItem(
          |      content = Text( "Sign in with GOV.UK Verify"),
          |      value = Some("govuk-verify"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text( "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity.")
          |      ))
          |    )
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios4 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "where-do-you-live",
          |  name: "where-do-you-live",
          |  fieldset: {
          |    legend: {
          |      text: "Where do you live?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  items: [
          |    {
          |      value: "england",
          |      text: "England"
          |    },
          |    {
          |      value: "scotland",
          |      text: "Scotland"
          |    },
          |    {
          |      value: "wales",
          |      text: "Wales"
          |    },
          |    {
          |      value: "northern-ireland",
          |      text: "Northern Ireland"
          |    },
          |    {
          |      divider: "or"
          |    },
          |    {
          |      value: "abroad",
          |      text: "I am a British citizen living abroad"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text( "England"), value = Some("england")),
          |    RadioItem(content = Text( "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text( "Wales"), value = Some("wales")),
          |    RadioItem(content = Text( "Northern Ireland"), value = Some("northern-ireland")),
          |    RadioItem(divider = Some("or")),
          |    RadioItem(content = Text( "I am a British citizen living abroad"), value = Some("abroad"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
      gouvukRadiosTwirl.print
    }

    "format GovukRadios5 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{% set emailHtml %}
          |{{ govukInput({
          |  id: "contact-by-email",
          |  name: "contact-by-email",
          |  type: "email",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Email address"
          |  },
          |  attributes: {
          |    spellcheck: "false"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{% set phoneHtml %}
          |{{ govukInput({
          |  id: "contact-by-phone",
          |  name: "contact-by-phone",
          |  type: "tel",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Phone number"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{% set textHtml %}
          |{{ govukInput({
          |  id: "contact-by-text",
          |  name: "contact-by-text",
          |  type: "tel",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Mobile phone number"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{{ govukRadios({
          |  idPrefix: "how-contacted-conditional",
          |  name: "how-contacted",
          |  fieldset: {
          |    legend: {
          |      text: "How would you prefer to be contacted?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "Select one option."
          |  },
          |  items: [
          |    {
          |      value: "email",
          |      text: "Email",
          |      conditional: {
          |        html: emailHtml
          |      }
          |    },
          |    {
          |      value: "phone",
          |      text: "Phone",
          |      conditional: {
          |        html: phoneHtml
          |      }
          |    },
          |    {
          |      value: "text",
          |      text: "Text message",
          |      conditional: {
          |        html: textHtml
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |
          |@emailHtml = {
          |  @GovukInput(Input(
          |    id = "contact-by-email",
          |    name = "contact-by-email",
          |    inputType = "email",
          |    label = Label(
          |      content = Text( "Email address")
          |    ),
          |    classes = "govuk-!-width-one-third",
          |    attributes = Map("spellcheck" -> "false")
          |  ))
          |}
          |
          |@phoneHtml = {
          |  @GovukInput(Input(
          |    id = "contact-by-phone",
          |    name = "contact-by-phone",
          |    inputType = "tel",
          |    label = Label(
          |      content = Text( "Phone number")
          |    ),
          |    classes = "govuk-!-width-one-third"
          |  ))
          |}
          |
          |@textHtml = {
          |  @GovukInput(Input(
          |    id = "contact-by-text",
          |    name = "contact-by-text",
          |    inputType = "tel",
          |    label = Label(
          |      content = Text( "Mobile phone number")
          |    ),
          |    classes = "govuk-!-width-one-third"
          |  ))
          |}
          |
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "How would you prefer to be contacted?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "Select one option.")
          |  )),
          |  idPrefix = Some("how-contacted-conditional"),
          |  name = "how-contacted",
          |  items = Seq(
          |    RadioItem(content = Text("Email"), value = Some("email"), conditionalHtml = Some(emailHtml)),
          |    RadioItem(content = Text("Phone"), value = Some("phone"), conditionalHtml = Some(phoneHtml)),
          |    RadioItem(content = Text("Text message"), value = Some("text"), conditionalHtml = Some(textHtml))
          |  )
          |))
          |""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios6 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--small",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Filter",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--m"
          |    }
          |  },
          |  items: [
          |    {
          |      value: "month",
          |      text: "Monthly"
          |    },
          |    {
          |      value: "year",
          |      text: "Yearly"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Filter"),
          |      classes = "govuk-fieldset__legend--m",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Monthly"),
          |      value = Some("month")
          |    ),
          |    RadioItem(
          |      content = Text( "Yearly"),
          |      value = Some("year")
          |    )
          |  ),
          |  classes = "govuk-radios--small"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios7 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  errorMessage: {
          |    text: "Select yes if you have changed your name"
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Select yes if you have changed your name")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Yes"),
          |      value = Some("yes")
          |    ),
          |    RadioItem(
          |      content = Text( "No"),
          |      value = Some("no")
          |    )
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadios       = formatPlay25(gouvukRadiosParsed.get.value)
      gouvukRadios.print
      gouvukRadios.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukSummaryList " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "name"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "date of birth"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact information"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact details"
          |          }
          |        ]
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content =
          |      $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = formatPlay25(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList1 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "name"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "date of birth"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact information"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact details"
          |          }
          |        ]
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content =
          |      $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = formatPlay25(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList2 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress)
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd)
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = formatPlay25(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList3 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  classes: 'govuk-summary-list--no-border',
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress)
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd)
          |    )
          |  ),
          |  classes = "govuk-summary-list--no-border"
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = formatPlay25(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukInput " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput1 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput2 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "20 character width"
          |  },
          |  classes: "govuk-input--width-20",
          |  id: "width-20",
          |  name: "width-20"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "10 character width"
          |  },
          |  classes: "govuk-input--width-10",
          |  id: "width-10",
          |  name: "width-10"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "5 character width"
          |  },
          |  classes: "govuk-input--width-5",
          |  id: "width-5",
          |  name: "width-5"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "4 character width"
          |  },
          |  classes: "govuk-input--width-4",
          |  id: "width-4",
          |  name: "width-4"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "3 character width"
          |  },
          |  classes: "govuk-input--width-3",
          |  id: "width-3",
          |  name: "width-3"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "2 character width"
          |  },
          |  classes: "govuk-input--width-2",
          |  id: "width-2",
          |  name: "width-2"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "width-20",
          |  name = "width-20",
          |  label = Label(
          |    content = Text( "20 character width")
          |  ),
          |  classes = "govuk-input--width-20"
          |))
          |@GovukInput(Input(
          |  id = "width-10",
          |  name = "width-10",
          |  label = Label(
          |    content = Text( "10 character width")
          |  ),
          |  classes = "govuk-input--width-10"
          |))
          |@GovukInput(Input(
          |  id = "width-5",
          |  name = "width-5",
          |  label = Label(
          |    content = Text( "5 character width")
          |  ),
          |  classes = "govuk-input--width-5"
          |))
          |@GovukInput(Input(
          |  id = "width-4",
          |  name = "width-4",
          |  label = Label(
          |    content = Text( "4 character width")
          |  ),
          |  classes = "govuk-input--width-4"
          |))
          |@GovukInput(Input(
          |  id = "width-3",
          |  name = "width-3",
          |  label = Label(
          |    content = Text( "3 character width")
          |  ),
          |  classes = "govuk-input--width-3"
          |))
          |@GovukInput(Input(
          |  id = "width-2",
          |  name = "width-2",
          |  label = Label(
          |    content = Text( "2 character width")
          |  ),
          |  classes = "govuk-input--width-2"
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput3 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Full width"
          |  },
          |  classes: "govuk-!-width-full",
          |  id: "full",
          |  name: "full"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Three-quarters width"
          |  },
          |  classes: "govuk-!-width-three-quarters",
          |  id: "three-quarters",
          |  name: "three-quarters"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Two-thirds width"
          |  },
          |  classes: "govuk-!-width-two-thirds",
          |  id: "two-thirds",
          |  name: "two-thirds"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-half width"
          |  },
          |  classes: "govuk-!-width-one-half",
          |  id: "one-half",
          |  name: "one-half"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-third width"
          |  },
          |  classes: "govuk-!-width-one-third",
          |  id: "one-third",
          |  name: "one-third"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-quarter width"
          |  },
          |  classes: "govuk-!-width-one-quarter",
          |  id: "one-quarter",
          |  name: "one-quarter"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "full",
          |  name = "full",
          |  label = Label(
          |    content = Text( "Full width")
          |  ),
          |  classes = "govuk-!-width-full"
          |))
          |@GovukInput(Input(
          |  id = "three-quarters",
          |  name = "three-quarters",
          |  label = Label(
          |    content = Text( "Three-quarters width")
          |  ),
          |  classes = "govuk-!-width-three-quarters"
          |))
          |@GovukInput(Input(
          |  id = "two-thirds",
          |  name = "two-thirds",
          |  label = Label(
          |    content = Text( "Two-thirds width")
          |  ),
          |  classes = "govuk-!-width-two-thirds"
          |))
          |@GovukInput(Input(
          |  id = "one-half",
          |  name = "one-half",
          |  label = Label(
          |    content = Text( "One-half width")
          |  ),
          |  classes = "govuk-!-width-one-half"
          |))
          |@GovukInput(Input(
          |  id = "one-third",
          |  name = "one-third",
          |  label = Label(
          |    content = Text( "One-third width")
          |  ),
          |  classes = "govuk-!-width-one-third"
          |))
          |@GovukInput(Input(
          |  id = "one-quarter",
          |  name = "one-quarter",
          |  label = Label(
          |    content = Text( "One-quarter width")
          |  ),
          |  classes = "govuk-!-width-one-quarter"
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput4 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  hint: {
          |    text: "The name you’ll use on promotional material."
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "The name you’ll use on promotional material.")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput5 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name",
          |  hint: {
          |    text: "The name you’ll use on promotional material."
          |  },
          |  errorMessage: {
          |    text: "Enter an event name"
          |  }
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "The name you’ll use on promotional material.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Enter an event name")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukInputTwirl  = formatPlay25(gouvukInputParsed.get.value)
      gouvukInputTwirl.print
      gouvukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukTextarea " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = formatPlay25(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea1 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = formatPlay25(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea2 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  rows: "8",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  rows = 8,
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = formatPlay25(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea3 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  },
          |  errorMessage: {
          |    text: "Enter more detail"
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Enter more detail")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = formatPlay25(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

  }

  "Play 2.6 formatter" should {

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
          |@this(govukBackLink: GovukBackLink)
          |
          |@()
          |@govukBackLink(BackLink(href = "#", content = Text( "Back")))""".stripMargin

      val gouvukBackLinkParsed = fastparse.parse(gouvukBackLinkNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukBackLinkTwirl  = format(gouvukBackLinkParsed.get.value)
      gouvukBackLinkTwirl.print
      gouvukBackLinkTwirl.trimSpaces shouldBe govukBackLinkTwirlExpected.trimSpaces
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
          |@this(govukButton: GovukButton)
          |
          |@()
          |@govukButton(Button(content = Text( "Save and continue")))""".stripMargin

      val gouvukButtonParsed = fastparse.parse(gouvukButtonNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukButtonTwirl  = format(gouvukButtonParsed.get.value)
      gouvukButtonTwirl.print
      gouvukButtonTwirl.trimSpaces shouldBe govukButtonTwirlExpected.trimSpaces
    }

    "format GovukErrorSummary " in {

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
          |@this(govukErrorSummary: GovukErrorSummary)
          |
          |@()
          |@govukErrorSummary(ErrorSummary(
          |  errorList =
          |    Seq(
          |      ErrorLink(
          |        href = Some("#passport-issued-error"),
          |        content = Text( "The date your passport was issued must be in the past")
          |      ),
          |      ErrorLink(
          |        href = Some("#postcode-error"),
          |        content = Text( "Enter a postcode, like AA1 1AA")
          |      )
          |    ),
          |  title = Text( "There is a problem")
          |))""".stripMargin

      val gouvukErrorSummaryParsed = fastparse.parse(gouvukErrorSummaryNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukErrorSummaryTwirl  = format(gouvukErrorSummaryParsed.get.value)
      gouvukErrorSummaryTwirl.print
      gouvukErrorSummaryTwirl.trimSpaces shouldBe govukErrorSummaryTwirlExpected.trimSpaces
    }

    "format GovukFieldset " in {

      val gouvukFieldsetNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |{% from "govuk/components/fieldset/macro.njk" import govukFieldset %}
          |
          |{% call govukFieldset({
          |  legend: {
          |    text: "What is your address?",
          |    classes: "govuk-fieldset__legend--xl",
          |    isPageHeading: true
          |  }
          |}) %}
          |
          |  {{ govukInput({
          |    label: {
          |      html: 'Building and street <span class="govuk-visually-hidden">line 1 of 2</span>'
          |    },
          |    id: "address-line-1",
          |    name: "address-line-1"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      html: '<span class="govuk-visually-hidden">Building and street line 2 of 2</span>'
          |    },
          |    id: "address-line-2",
          |    name: "address-line-2"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "Town or city"
          |    },
          |    classes: "govuk-!-width-two-thirds",
          |    id: "address-town",
          |    name: "address-town"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "County"
          |    },
          |    classes: "govuk-!-width-two-thirds",
          |    id: "address-county",
          |    name: "address-county"
          |  }) }}
          |
          |  {{ govukInput({
          |    label: {
          |      text: "Postcode"
          |    },
          |    classes: "govuk-input--width-10",
          |    id: "address-postcode",
          |    name: "address-postcode"
          |  }) }}
          |
          |{% endcall %}""".stripMargin

      val govukFieldsetTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput,
          |      govukFieldset: GovukFieldset)
          |
          |@()
          |@govukFieldset(Fieldset(
          |  legend =
          |    Some(
          |      Legend(
          |        content = Text( "What is your address?"),
          |        classes = "govuk-fieldset__legend--xl",
          |        isPageHeading = true
          |      )),
          |  html = html
          |))
          |
          |@html = {
          |  @govukInput(Input(
          |    id = "address-line-1",
          |    name = "address-line-1",
          |    label = Label(content = $htmlContentBegin Building and street <span class="govuk-visually-hidden">line 1 of 2</span>$htmlContentEnd)
          |  ))
          |  @govukInput(Input(
          |    id = "address-line-2",
          |    name = "address-line-2",
          |    label = Label(content = $htmlContentBegin <span class="govuk-visually-hidden">Building and street line 2 of 2</span>$htmlContentEnd)
          |  ))
          |  @govukInput(Input(
          |    id = "address-town",
          |    name = "address-town",
          |    label = Label(content = Text( "Town or city")),
          |    classes = "govuk-!-width-two-thirds"
          |  ))
          |  @govukInput(Input(
          |    id = "address-county",
          |    name = "address-county",
          |    label = Label(content = Text( "County")),
          |    classes = "govuk-!-width-two-thirds"
          |  ))
          |  @govukInput(Input(
          |    id = "address-postcode",
          |    name = "address-postcode",
          |    label = Label(content = Text( "Postcode")),
          |    classes = "govuk-input--width-10"
          |  ))
          |}""".stripMargin

      val gouvukFieldsetParsed = fastparse.parse(gouvukFieldsetNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukFieldsetTwirl  = format(gouvukFieldsetParsed.get.value)
      gouvukFieldsetTwirl.print
      gouvukFieldsetTwirl.trimSpaces shouldBe govukFieldsetTwirlExpected.trimSpaces
    }

    "format GovukFooter " in {

      val gouvukFooterNunjucks =
        """{% from "govuk/components/footer/macro.njk" import govukFooter %}
          |
          |{{ govukFooter({}) }}""".stripMargin

      val govukFooterTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukFooter: GovukFooter)
          |
          |@()
          |@govukFooter()""".stripMargin

      val gouvukFooterParsed = fastparse.parse(gouvukFooterNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukFooterTwirl  = format(gouvukFooterParsed.get.value)
      gouvukFooterTwirl.print
      gouvukFooterTwirl.trimSpaces shouldBe govukFooterTwirlExpected.trimSpaces
    }

    "format GovukHeader " in {

      val gouvukHeaderNunjucks =
        """{% from "govuk/components/header/macro.njk" import govukHeader %}
          |
          |{{ govukHeader({
          |  homepageUrl: "#"
          |}) }}""".stripMargin

      val govukHeaderTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukHeader: GovukHeader)
          |
          |@()
          |@govukHeader(Header(
          |  homepageUrl = Some("#")
          |))""".stripMargin

      val gouvukHeaderParsed = fastparse.parse(gouvukHeaderNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukHeaderTwirl  = format(gouvukHeaderParsed.get.value)
      gouvukHeaderTwirl.print
      gouvukHeaderTwirl.trimSpaces shouldBe govukHeaderTwirlExpected.trimSpaces
    }

    "format GovukPanel " in {

      val gouvukPanelNunjucks =
        """{% from "govuk/components/panel/macro.njk" import govukPanel %}
          |
          |{{ govukPanel({
          |  titleText: "Application complete",
          |  html: "Your reference number<br><strong>HDJ2123F</strong>"
          |}) }}""".stripMargin

      val govukPanelTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukPanel: GovukPanel)
          |
          |@()
          |@govukPanel(Panel(
          |  title = Text( "Application complete"),
          |  content = $htmlContentBegin Your reference number<br><strong>HDJ2123F</strong>$htmlContentEnd
          |))""".stripMargin

      val gouvukPanelParsed = fastparse.parse(gouvukPanelNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukPanelTwirl  = format(gouvukPanelParsed.get.value)
      gouvukPanelTwirl.print
      gouvukPanelTwirl.trimSpaces shouldBe govukPanelTwirlExpected.trimSpaces
    }

    "format GovukRadios " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text( "Yes"), value = Some("yes")),
          |    RadioItem(content = Text( "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios1 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "where-do-you-live",
          |  name: "where-do-you-live",
          |  fieldset: {
          |    legend: {
          |      text: "Where do you live?",
          |      classes: "govuk-fieldset__legend--xl",
          |      isPageHeading: true
          |    }
          |  },
          |  items: [
          |    {
          |      value: "england",
          |      text: "England"
          |    },
          |    {
          |      value: "scotland",
          |      text: "Scotland"
          |    },
          |    {
          |      value: "wales",
          |      text: "Wales"
          |    },
          |    {
          |      value: "northern-ireland",
          |      text: "Northern Ireland"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text( "England"), value = Some("england")),
          |    RadioItem(content = Text( "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text( "Wales"), value = Some("wales")),
          |    RadioItem(content = Text( "Northern Ireland"), value = Some("northern-ireland"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios2 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text( "Yes"), value = Some("yes")),
          |    RadioItem(content = Text( "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios3 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "sign-in",
          |  name: "sign-in",
          |  fieldset: {
          |    legend: {
          |      text: "How do you want to sign in?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "You’ll need an account to prove your identity and complete your Self Assessment."
          |  },
          |  items: [
          |    {
          |      value: "government-gateway",
          |      text: "Sign in with Government Gateway",
          |      label: {
          |        classes: "govuk-label--s"
          |      },
          |      hint: {
          |        text: "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before."
          |      }
          |    },
          |    {
          |      value: "govuk-verify",
          |      text: "Sign in with GOV.UK Verify",
          |      label: {
          |        classes: "govuk-label--s"
          |      },
          |      hint: {
          |        text: "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity."
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "How do you want to sign in?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "You’ll need an account to prove your identity and complete your Self Assessment.")
          |  )),
          |  idPrefix = Some("sign-in"),
          |  name = "sign-in",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Sign in with Government Gateway"),
          |      value = Some("government-gateway"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text( "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before.")
          |      ))
          |    ),
          |    RadioItem(
          |      content = Text( "Sign in with GOV.UK Verify"),
          |      value = Some("govuk-verify"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text( "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity.")
          |      ))
          |    )
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios4 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  idPrefix: "where-do-you-live",
          |  name: "where-do-you-live",
          |  fieldset: {
          |    legend: {
          |      text: "Where do you live?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  items: [
          |    {
          |      value: "england",
          |      text: "England"
          |    },
          |    {
          |      value: "scotland",
          |      text: "Scotland"
          |    },
          |    {
          |      value: "wales",
          |      text: "Wales"
          |    },
          |    {
          |      value: "northern-ireland",
          |      text: "Northern Ireland"
          |    },
          |    {
          |      divider: "or"
          |    },
          |    {
          |      value: "abroad",
          |      text: "I am a British citizen living abroad"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text( "England"), value = Some("england")),
          |    RadioItem(content = Text( "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text( "Wales"), value = Some("wales")),
          |    RadioItem(content = Text( "Northern Ireland"), value = Some("northern-ireland")),
          |    RadioItem(divider = Some("or")),
          |    RadioItem(content = Text( "I am a British citizen living abroad"), value = Some("abroad"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios5 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{% set emailHtml %}
          |{{ govukInput({
          |  id: "contact-by-email",
          |  name: "contact-by-email",
          |  type: "email",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Email address"
          |  },
          |  attributes: {
          |    spellcheck: "false"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{% set phoneHtml %}
          |{{ govukInput({
          |  id: "contact-by-phone",
          |  name: "contact-by-phone",
          |  type: "tel",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Phone number"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{% set textHtml %}
          |{{ govukInput({
          |  id: "contact-by-text",
          |  name: "contact-by-text",
          |  type: "tel",
          |  classes: "govuk-!-width-one-third",
          |  label: {
          |    text: "Mobile phone number"
          |  }
          |}) }}
          |{% endset -%}
          |
          |{{ govukRadios({
          |  idPrefix: "how-contacted-conditional",
          |  name: "how-contacted",
          |  fieldset: {
          |    legend: {
          |      text: "How would you prefer to be contacted?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "Select one option."
          |  },
          |  items: [
          |    {
          |      value: "email",
          |      text: "Email",
          |      conditional: {
          |        html: emailHtml
          |      }
          |    },
          |    {
          |      value: "phone",
          |      text: "Phone",
          |      conditional: {
          |        html: phoneHtml
          |      }
          |    },
          |    {
          |      value: "text",
          |      text: "Text message",
          |      conditional: {
          |        html: textHtml
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios : GovukRadios,
          |      govukInput : GovukInput)
          |
          |@()
          |
          |@emailHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-email",
          |    name = "contact-by-email",
          |    inputType = "email",
          |    label = Label(
          |      content = Text( "Email address")
          |    ),
          |    classes = "govuk-!-width-one-third",
          |    attributes = Map("spellcheck" -> "false")
          |  ))
          |}
          |
          |@phoneHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-phone",
          |    name = "contact-by-phone",
          |    inputType = "tel",
          |    label = Label(
          |      content = Text( "Phone number")
          |    ),
          |    classes = "govuk-!-width-one-third"
          |  ))
          |}
          |
          |@textHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-text",
          |    name = "contact-by-text",
          |    inputType = "tel",
          |    label = Label(
          |      content = Text( "Mobile phone number")
          |    ),
          |    classes = "govuk-!-width-one-third"
          |  ))
          |}
          |
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "How would you prefer to be contacted?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "Select one option.")
          |  )),
          |  idPrefix = Some("how-contacted-conditional"),
          |  name = "how-contacted",
          |  items = Seq(
          |    RadioItem(content = Text("Email"), value = Some("email"), conditionalHtml = Some(emailHtml)),
          |    RadioItem(content = Text("Phone"), value = Some("phone"), conditionalHtml = Some(phoneHtml)),
          |    RadioItem(content = Text("Text message"), value = Some("text"), conditionalHtml = Some(textHtml))
          |  )
          |))
          |""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios6 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--small",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Filter",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--m"
          |    }
          |  },
          |  items: [
          |    {
          |      value: "month",
          |      text: "Monthly"
          |    },
          |    {
          |      value: "year",
          |      text: "Yearly"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Filter"),
          |      classes = "govuk-fieldset__legend--m",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Monthly"),
          |      value = Some("month")
          |    ),
          |    RadioItem(
          |      content = Text( "Yearly"),
          |      value = Some("year")
          |    )
          |  ),
          |  classes = "govuk-radios--small"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukRadios7 " in {

      val gouvukRadiosNunjucks =
        """{% from "govuk/components/radios/macro.njk" import govukRadios %}
          |
          |{{ govukRadios({
          |  classes: "govuk-radios--inline",
          |  idPrefix: "changed-name",
          |  name: "changed-name",
          |  fieldset: {
          |    legend: {
          |      text: "Have you changed your name?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "This includes changing your last name or spelling your name differently."
          |  },
          |  errorMessage: {
          |    text: "Select yes if you have changed your name"
          |  },
          |  items: [
          |    {
          |      value: "yes",
          |      text: "Yes"
          |    },
          |    {
          |      value: "no",
          |      text: "No"
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukRadiosTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukRadios: GovukRadios)
          |
          |@()
          |@govukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text( "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text( "This includes changing your last name or spelling your name differently.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Select yes if you have changed your name")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text( "Yes"),
          |      value = Some("yes")
          |    ),
          |    RadioItem(
          |      content = Text( "No"),
          |      value = Some("no")
          |    )
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed = fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukRadiosTwirl  = format(gouvukRadiosParsed.get.value)
      gouvukRadiosTwirl.print
      gouvukRadiosTwirl.trimSpaces shouldBe govukRadiosTwirlExpected.trimSpaces
    }

    "format GovukSummaryList " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "name"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "date of birth"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact information"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact details"
          |          }
          |        ]
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = format(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList1 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "name"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "date of birth"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact information"
          |          }
          |        ]
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      },
          |      actions: {
          |        items: [
          |          {
          |            href: "#",
          |            text: "Change",
          |            visuallyHiddenText: "contact details"
          |          }
          |        ]
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text( "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = format(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList2 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress)
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd)
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = format(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukSummaryList3 " in {

      val gouvukSummaryListNunjucks =
        """{% from "govuk/components/summary-list/macro.njk" import govukSummaryList %}
          |
          |{{ govukSummaryList({
          |  classes: 'govuk-summary-list--no-border',
          |  rows: [
          |    {
          |      key: {
          |        text: "Name"
          |      },
          |      value: {
          |        text: "Sarah Philips"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Date of birth"
          |      },
          |      value: {
          |        text: "5 January 1978"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact information"
          |      },
          |      value: {
          |        html: "72 Guild Street<br>London<br>SE23 6FH"
          |      }
          |    },
          |    {
          |      key: {
          |        text: "Contact details"
          |      },
          |      value: {
          |        html: '<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>'
          |      }
          |    }
          |  ]
          |}) }}""".stripMargin

      val govukSummaryListTwirlExpected =
        s"""@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text( "Name")),
          |      value = Value(content = Text( "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Date of birth")),
          |      value = Value(content = Text( "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact information")),
          |      value = Value(content = $htmlAddress)
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text( "Contact details")),
          |      value = Value(content = $htmlContentBegin<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>$htmlContentEnd)
          |    )
          |  ),
          |  classes = "govuk-summary-list--no-border"
          |))""".stripMargin

      val gouvukSummaryListParsed = fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukSummaryListTwirl  = format(gouvukSummaryListParsed.get.value)
      gouvukSummaryListTwirl.print
      gouvukSummaryListTwirl.trimSpaces shouldBe govukSummaryListTwirlExpected.trimSpaces
    }

    "format GovukInput " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  )
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput1 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  )
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput2 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "20 character width"
          |  },
          |  classes: "govuk-input--width-20",
          |  id: "width-20",
          |  name: "width-20"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "10 character width"
          |  },
          |  classes: "govuk-input--width-10",
          |  id: "width-10",
          |  name: "width-10"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "5 character width"
          |  },
          |  classes: "govuk-input--width-5",
          |  id: "width-5",
          |  name: "width-5"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "4 character width"
          |  },
          |  classes: "govuk-input--width-4",
          |  id: "width-4",
          |  name: "width-4"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "3 character width"
          |  },
          |  classes: "govuk-input--width-3",
          |  id: "width-3",
          |  name: "width-3"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "2 character width"
          |  },
          |  classes: "govuk-input--width-2",
          |  id: "width-2",
          |  name: "width-2"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "width-20",
          |  name = "width-20",
          |  label = Label(
          |    content = Text( "20 character width")
          |  ),
          |  classes = "govuk-input--width-20"
          |))
          |@govukInput(Input(
          |  id = "width-10",
          |  name = "width-10",
          |  label = Label(
          |    content = Text( "10 character width")
          |  ),
          |  classes = "govuk-input--width-10"
          |))
          |@govukInput(Input(
          |  id = "width-5",
          |  name = "width-5",
          |  label = Label(
          |    content = Text( "5 character width")
          |  ),
          |  classes = "govuk-input--width-5"
          |))
          |@govukInput(Input(
          |  id = "width-4",
          |  name = "width-4",
          |  label = Label(
          |    content = Text( "4 character width")
          |  ),
          |  classes = "govuk-input--width-4"
          |))
          |@govukInput(Input(
          |  id = "width-3",
          |  name = "width-3",
          |  label = Label(
          |    content = Text( "3 character width")
          |  ),
          |  classes = "govuk-input--width-3"
          |))
          |@govukInput(Input(
          |  id = "width-2",
          |  name = "width-2",
          |  label = Label(
          |    content = Text( "2 character width")
          |  ),
          |  classes = "govuk-input--width-2"
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput3 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Full width"
          |  },
          |  classes: "govuk-!-width-full",
          |  id: "full",
          |  name: "full"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Three-quarters width"
          |  },
          |  classes: "govuk-!-width-three-quarters",
          |  id: "three-quarters",
          |  name: "three-quarters"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Two-thirds width"
          |  },
          |  classes: "govuk-!-width-two-thirds",
          |  id: "two-thirds",
          |  name: "two-thirds"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-half width"
          |  },
          |  classes: "govuk-!-width-one-half",
          |  id: "one-half",
          |  name: "one-half"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-third width"
          |  },
          |  classes: "govuk-!-width-one-third",
          |  id: "one-third",
          |  name: "one-third"
          |}) }}
          |
          |{{ govukInput({
          |  label: {
          |    text: "One-quarter width"
          |  },
          |  classes: "govuk-!-width-one-quarter",
          |  id: "one-quarter",
          |  name: "one-quarter"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "full",
          |  name = "full",
          |  label = Label(
          |    content = Text( "Full width")
          |  ),
          |  classes = "govuk-!-width-full"
          |))
          |@govukInput(Input(
          |  id = "three-quarters",
          |  name = "three-quarters",
          |  label = Label(
          |    content = Text( "Three-quarters width")
          |  ),
          |  classes = "govuk-!-width-three-quarters"
          |))
          |@govukInput(Input(
          |  id = "two-thirds",
          |  name = "two-thirds",
          |  label = Label(
          |    content = Text( "Two-thirds width")
          |  ),
          |  classes = "govuk-!-width-two-thirds"
          |))
          |@govukInput(Input(
          |  id = "one-half",
          |  name = "one-half",
          |  label = Label(
          |    content = Text( "One-half width")
          |  ),
          |  classes = "govuk-!-width-one-half"
          |))
          |@govukInput(Input(
          |  id = "one-third",
          |  name = "one-third",
          |  label = Label(
          |    content = Text( "One-third width")
          |  ),
          |  classes = "govuk-!-width-one-third"
          |))
          |@govukInput(Input(
          |  id = "one-quarter",
          |  name = "one-quarter",
          |  label = Label(
          |    content = Text( "One-quarter width")
          |  ),
          |  classes = "govuk-!-width-one-quarter"
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput4 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  hint: {
          |    text: "The name you’ll use on promotional material."
          |  },
          |  id: "event-name",
          |  name: "event-name"
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "The name you’ll use on promotional material.")
          |  ))
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukInput5 " in {

      val gouvukInputNunjucks =
        """{% from "govuk/components/input/macro.njk" import govukInput %}
          |
          |{{ govukInput({
          |  label: {
          |    text: "Event name"
          |  },
          |  id: "event-name",
          |  name: "event-name",
          |  hint: {
          |    text: "The name you’ll use on promotional material."
          |  },
          |  errorMessage: {
          |    text: "Enter an event name"
          |  }
          |}) }}""".stripMargin

      val govukInputTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@govukInput(Input(
          |  id = "event-name",
          |  name = "event-name",
          |  label = Label(
          |    content = Text( "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "The name you’ll use on promotional material.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Enter an event name")
          |  ))
          |))""".stripMargin

      val govukInputParsed = fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))
      val govukInputTwirl  = format(govukInputParsed.get.value)
      govukInputTwirl.print
      govukInputTwirl.trimSpaces shouldBe govukInputTwirlExpected.trimSpaces
    }

    "format GovukTextarea " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukTextarea: GovukTextarea)
          |
          |@()
          |@govukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = format(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea1 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukTextarea: GovukTextarea)
          |
          |@()
          |@govukTextarea(Textarea(
            |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = format(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea2 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  rows: "8",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukTextarea: GovukTextarea)
          |
          |@()
          |@govukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  rows = 8,
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = format(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

    "format GovukTextarea3 " in {

      val gouvukTextareaNunjucks =
        """{% from "govuk/components/textarea/macro.njk" import govukTextarea %}
          |
          |{{ govukTextarea({
          |  name: "more-detail",
          |  id: "more-detail",
          |  label: {
          |    text: "Can you provide more detail?"
          |  },
          |  hint: {
          |    text: "Do not include personal or financial information, like your National Insurance number or credit card details."
          |  },
          |  errorMessage: {
          |    text: "Enter more detail"
          |  }
          |}) }}""".stripMargin

      val govukTextareaTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukTextarea: GovukTextarea)
          |
          |@()
          |@govukTextarea(Textarea(
          |  id = "more-detail",
          |  name = "more-detail",
          |  label = Label(
          |    content = Text( "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text( "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text( "Enter more detail")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed = fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukTextareaTwirl  = format(gouvukTextareaParsed.get.value)
      gouvukTextareaTwirl.print
      gouvukTextareaTwirl.trimSpaces shouldBe govukTextareaTwirlExpected.trimSpaces
    }

  }

}
