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
import uk.gov.hmrc.govukfrontend.examples.TwirlFormatter._

class TwirlFormatterSpec extends WordSpec with Matchers {

  implicit class StringUtil(string: String) {

    def trimSpaces = string.replaceAll("""\s+""", "")

    def print: Unit = println(string)
  }

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
          |@GovukBackLink(BackLink(href = "#", content = Text(value = "Back")))""".stripMargin

      val gouvukBackLinkParsed = fastparse.parse(gouvukBackLinkNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukBackLinkTwirl  = formatPlay25(gouvukBackLinkParsed.get.value)
      gouvukBackLinkTwirl.trimSpaces shouldBe govukBackLinkTwirlExpected.trimSpaces
      gouvukBackLinkTwirl.print
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
          |@GovukButton(Button(content = Text(value = "Save and continue")))""".stripMargin

      val gouvukButtonParsed = fastparse.parse(gouvukButtonNunjucks, NunjucksParser.nunjucksParser(_))
      val gouvukButtonTwirl  = formatPlay25(gouvukButtonParsed.get.value)
      gouvukButtonTwirl.trimSpaces shouldBe govukButtonTwirlExpected.trimSpaces
      gouvukButtonTwirl.print
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

      val gouvukErrorSummaryParsed = fastparse.parse(gouvukErrorSummaryNunjucks, NunjucksParser.nunjucksParser(_))
      val govukErrorSummaryTwirl   = formatPlay25(gouvukErrorSummaryParsed.get.value)
      govukErrorSummaryTwirl.trimSpaces shouldBe govukErrorSummaryTwirlExpected.trimSpaces
      govukErrorSummaryTwirl.print
    }

    "format GovukFieldset " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@GovukFieldset(Fieldset(
          |  legend =
          |    Some(
          |      Legend(
          |        content = Text(value = "What is your address?"),
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
          |    label = Label(content = HtmlContent(value = \"\"\"Building and street <span class="govuk-visually-hidden">line 1 of 2</span>\"\"\"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-line-2",
          |    name = "address-line-2",
          |    label = Label(content = HtmlContent(value = \"\"\"<span class="govuk-visually-hidden">Building and street line 2 of 2</span>\"\"\"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-town",
          |    name = "address-town",
          |    classes = "govuk-!-width-two-thirds",
          |    label = Label(content = Text(value = "Town or city"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-county",
          |    name = "address-county",
          |    classes = "govuk-!-width-two-thirds",
          |    label = Label(content = Text(value = "County"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-postcode",
          |    name = "address-postcode",
          |    classes = "govuk-input--width-10",
          |    label = Label(content = Text(value = "Postcode"))
          |  ))
          |}""".stripMargin

      val gouvukFieldsetParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukFieldsetNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukFieldsetParsed.get.value) shouldBe govukFieldsetTwirlExpected
    }

    "format GovukFooter " ignore {

      val gouvukFooterNunjucks =
        """{% from "govuk/components/footer/macro.njk" import govukFooter %}
          |
          |{{ govukFooter({}) }}""".stripMargin

      val govukFooterTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukFooter()""".stripMargin

      val gouvukFooterParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukFooterNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukFooterParsed.get.value) shouldBe govukFooterTwirlExpected
    }

    "format GovukHeader " ignore {

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

      val gouvukHeaderParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukHeaderNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukHeaderParsed.get.value) shouldBe govukHeaderTwirlExpected
    }

    "format GovukPanel " ignore {

      val gouvukPanelNunjucks =
        """{% from "govuk/components/panel/macro.njk" import govukPanel %}
          |
          |{{ govukPanel({
          |  titleText: "Application complete",
          |  html: "Your reference number<br><strong>HDJ2123F</strong>"
          |}) }}""".stripMargin

      val govukPanelTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukPanel(Panel(
          |  title = Text(value = "Application complete"),
          |  content = HtmlContent(value = "Your reference number<br><strong>HDJ2123F</strong>")
          |))""".stripMargin

      val gouvukPanelParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukPanelNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukPanelParsed.get.value) shouldBe govukPanelTwirlExpected
    }

    "format GovukRadios " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Yes"), value = Some("yes")),
          |    RadioItem(content = Text(value = "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios1 " ignore {

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
          |      content = Text(value = "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text(value = "England"), value = Some("england")),
          |    RadioItem(content = Text(value = "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text(value = "Wales"), value = Some("wales")),
          |    RadioItem(content = Text(value = "Northern Ireland"), value = Some("northern-ireland"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios2 " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Yes"), value = Some("yes")),
          |    RadioItem(content = Text(value = "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline",
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios3 " ignore {

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
          |      content = Text(value = "How do you want to sign in?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "You’ll need an account to prove your identity and complete your Self Assessment.")
          |  )),
          |  idPrefix = Some("sign-in"),
          |  name = "sign-in",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Sign in with Government Gateway"),
          |      value = Some("government-gateway"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text(value = "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before.")
          |      ))
          |    ),
          |    RadioItem(
          |      content = Text(value = "Sign in with GOV.UK Verify"),
          |      value = Some("govuk-verify"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text(value = "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity.")
          |      ))
          |    )
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios4 " ignore {

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
          |      content = Text(value = "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text(value = "England"), value = Some("england")),
          |    RadioItem(content = Text(value = "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text(value = "Wales"), value = Some("wales")),
          |    RadioItem(content = Text(value = "Northern Ireland"), value = Some("northern-ireland")),
          |    RadioItem(divider = Some("or")),
          |    RadioItem(content = Text(value = "I am a British citizen living abroad"), value = Some("abroad"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios5 " ignore {

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
          |@this(govukInput: GovukInput)
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text(value = "How would you prefer to be contacted?"),
          |      isPageHeading = true,
          |      classes = "govuk-fieldset__legend--xl"
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "Select one option.")
          |  )),
          |  idPrefix = Some("how-contacted-conditional"),
          |  name = "how-contacted",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Email", value = Some("email")), conditionalHtml = Some(emailHtml)),
          |    RadioItem(content = Text(value = "Phone", value = Some("phone")), conditionalHtml = Some(phoneHtml)),
          |    RadioItem(content = Text(value = "Text message", value = Some("text")), conditionalHtml = Some(textHtml))
          |  )
          |))
          |
          |@emailHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-email",
          |    name = "contact-by-email",
          |    inputType = "email",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Email address")
          |    ),
          |    attributes = Map("spellcheck" -> "false")
          |  ))
          |}
          |
          |@phoneHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-phone",
          |    name = "contact-by-phone",
          |    inputType = "tel",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Phone number")
          |    )
          |  ))
          |}
          |
          |@textHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-text",
          |    name = "contact-by-text",
          |    inputType = "tel",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Mobile phone number")
          |    )
          |  ))
          |}""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios6 " ignore {

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
          |      content = Text(value = "Filter"),
          |      classes = "govuk-fieldset__legend--m",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Monthly"),
          |      value = Some("month")
          |    ),
          |    RadioItem(
          |      content = Text(value = "Yearly"),
          |      value = Some("year")
          |    )
          |  ),
          |  classes = "govuk-radios--small"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios7 " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Select yes if you have changed your name")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Yes"),
          |      value = Some("yes")
          |    ),
          |    RadioItem(
          |      content = Text(value = "No"),
          |      value = Some("no")
          |    )
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukSummaryList " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList1 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList2 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>"))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList3 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@()
          |@GovukSummaryList(SummaryList(
          |  classes = "govuk-summary-list--no-border",
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>"))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukInput " ignore {

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
          |    content = Text(value = "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput1 " ignore {

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
          |    content = Text(value = "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput2 " ignore {

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
          |  classes = "govuk-input--width-20",
          |  label = Label(
          |    content = Text(value = "20 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-10",
          |  name = "width-10",
          |  classes = "govuk-input--width-10",
          |  label = Label(
          |    content = Text(value = "10 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-5",
          |  name = "width-5",
          |  classes = "govuk-input--width-5",
          |  label = Label(
          |    content = Text(value = "5 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-4",
          |  name = "width-4",
          |  classes = "govuk-input--width-4",
          |  label = Label(
          |    content = Text(value = "4 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-3",
          |  name = "width-3",
          |  classes = "govuk-input--width-3",
          |  label = Label(
          |    content = Text(value = "3 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-2",
          |  name = "width-2",
          |  classes = "govuk-input--width-2",
          |  label = Label(
          |    content = Text(value = "2 character width")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput3 " ignore {

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
          |  classes = "govuk-!-width-full",
          |  label = Label(
          |    content = Text(value = "Full width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "three-quarters",
          |  name = "three-quarters",
          |  classes = "govuk-!-width-three-quarters",
          |  label = Label(
          |    content = Text(value = "Three-quarters width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "two-thirds",
          |  name = "two-thirds",
          |  classes = "govuk-!-width-two-thirds",
          |  label = Label(
          |    content = Text(value = "Two-thirds width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-half",
          |  name = "one-half",
          |  classes = "govuk-!-width-one-half",
          |  label = Label(
          |    content = Text(value = "One-half width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-third",
          |  name = "one-third",
          |  classes = "govuk-!-width-one-third",
          |  label = Label(
          |    content = Text(value = "One-third width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-quarter",
          |  name = "one-quarter",
          |  classes = "govuk-!-width-one-quarter",
          |  label = Label(
          |    content = Text(value = "One-quarter width")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput4 " ignore {

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
          |    content = Text(value = "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "The name you’ll use on promotional material.")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput5 " ignore {

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
          |    content = Text(value = "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "The name you’ll use on promotional material.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Enter an event name")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukTextarea " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea1 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea2 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  rows = 8,
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea3 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Enter more detail")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

  }

  "Play 2.6 formatter" should {

    "format GovukBackLink " ignore {

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
          |@govukBackLink(BackLink(href = "#", content = Text(value = "Back")))""".stripMargin

      val gouvukBackLinkParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukBackLinkNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukBackLinkParsed.get.value) shouldBe govukBackLinkTwirlExpected
    }

    "format GovukButton " ignore {

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
          |@govukButton(Button(content = Text(value = "Save and continue")))""".stripMargin

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
          |@this(govukErrorSummary: GovukErrorSummary)
          |
          |@()
          |@govukErrorSummary(ErrorSummary(
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

    "format GovukFieldset " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukInput: GovukInput)
          |
          |@()
          |@GovukFieldset(Fieldset(
          |  legend =
          |    Some(
          |      Legend(
          |        content = Text(value = "What is your address?"),
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
          |    label = Label(content = HtmlContent(value = "Building and street <span class="govuk-visually-hidden">line 1 of 2</span>"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-line-2",
          |    name = "address-line-2",
          |    label = Label(content = HtmlContent(value = "<span class="govuk-visually-hidden">Building and street line 2 of 2</span>"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-town",
          |    name = "address-town",
          |    classes = "govuk-!-width-two-thirds",
          |    label = Label(content = Text(value = "Town or city"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-county",
          |    name = "address-county",
          |    classes = "govuk-!-width-two-thirds",
          |    label = Label(content = Text(value = "County"))
          |  ))
          |  @govukInput(Input(
          |    id = "address-postcode",
          |    name = "address-postcode",
          |    classes = "govuk-input--width-10",
          |    label = Label(content = Text(value = "Postcode"))
          |  ))
          |}""".stripMargin

      val gouvukFieldsetParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukFieldsetNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukFieldsetParsed.get.value) shouldBe govukFieldsetTwirlExpected
    }

    "format GovukFooter " ignore {

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

      val gouvukFooterParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukFooterNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukFooterParsed.get.value) shouldBe govukFooterTwirlExpected
    }

    "format GovukHeader " ignore {

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

      val gouvukHeaderParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukHeaderNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukHeaderParsed.get.value) shouldBe govukHeaderTwirlExpected
    }

    "format GovukPanel " ignore {

      val gouvukPanelNunjucks =
        """{% from "govuk/components/panel/macro.njk" import govukPanel %}
          |
          |{{ govukPanel({
          |  titleText: "Application complete",
          |  html: "Your reference number<br><strong>HDJ2123F</strong>"
          |}) }}""".stripMargin

      val govukPanelTwirlExpected =
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukPanel: GovukPanel)
          |
          |@()
          |@govukPanel(Panel(
          |  title = Text(value = "Application complete"),
          |  content = HtmlContent(value = "Your reference number<br><strong>HDJ2123F</strong>")
          |))""".stripMargin

      val gouvukPanelParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukPanelNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukPanelParsed.get.value) shouldBe govukPanelTwirlExpected
    }

    "format GovukRadios " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Yes"), value = Some("yes")),
          |    RadioItem(content = Text(value = "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios1 " ignore {

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
          |      content = Text(value = "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text(value = "England"), value = Some("england")),
          |    RadioItem(content = Text(value = "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text(value = "Wales"), value = Some("wales")),
          |    RadioItem(content = Text(value = "Northern Ireland"), value = Some("northern-ireland"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios2 " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Yes"), value = Some("yes")),
          |    RadioItem(content = Text(value = "No"), value = Some("no"))
          |  ),
          |  classes = "govuk-radios--inline",
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios3 " ignore {

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
          |      content = Text(value = "How do you want to sign in?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "You’ll need an account to prove your identity and complete your Self Assessment.")
          |  )),
          |  idPrefix = Some("sign-in"),
          |  name = "sign-in",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Sign in with Government Gateway"),
          |      value = Some("government-gateway"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text(value = "You’ll have a user ID if you’ve registered for Self Assessment or filed a tax return online before.")
          |      ))
          |    ),
          |    RadioItem(
          |      content = Text(value = "Sign in with GOV.UK Verify"),
          |      value = Some("govuk-verify"),
          |      label = Some(Label(classes = "govuk-label--s")),
          |      hint = Some(Hint(
          |        content = Text(value = "You’ll have an account if you’ve already proved your identity with either Barclays, CitizenSafe, Digidentity, Experian, Post Office, Royal Mail or SecureIdentity.")
          |      ))
          |    )
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios4 " ignore {

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
          |      content = Text(value = "Where do you live?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("where-do-you-live"),
          |  name = "where-do-you-live",
          |  items = Seq(
          |    RadioItem(content = Text(value = "England"), value = Some("england")),
          |    RadioItem(content = Text(value = "Scotland"), value = Some("scotland")),
          |    RadioItem(content = Text(value = "Wales"), value = Some("wales")),
          |    RadioItem(content = Text(value = "Northern Ireland"), value = Some("northern-ireland")),
          |    RadioItem(divider = Some("or")),
          |    RadioItem(content = Text(value = "I am a British citizen living abroad"), value = Some("abroad"))
          |  )
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios5 " ignore {

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
          |@this(govukInput: GovukInput)
          |
          |@()
          |@GovukRadios(Radios(
          |  fieldset = Some(Fieldset(
          |    legend = Some(Legend(
          |      content = Text(value = "How would you prefer to be contacted?"),
          |      isPageHeading = true,
          |      classes = "govuk-fieldset__legend--xl"
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "Select one option.")
          |  )),
          |  idPrefix = Some("how-contacted-conditional"),
          |  name = "how-contacted",
          |  items = Seq(
          |    RadioItem(content = Text(value = "Email", value = Some("email")), conditionalHtml = Some(emailHtml)),
          |    RadioItem(content = Text(value = "Phone", value = Some("phone")), conditionalHtml = Some(phoneHtml)),
          |    RadioItem(content = Text(value = "Text message", value = Some("text")), conditionalHtml = Some(textHtml))
          |  )
          |))
          |
          |@emailHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-email",
          |    name = "contact-by-email",
          |    inputType = "email",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Email address")
          |    ),
          |    attributes = Map("spellcheck" -> "false")
          |  ))
          |}
          |
          |@phoneHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-phone",
          |    name = "contact-by-phone",
          |    inputType = "tel",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Phone number")
          |    )
          |  ))
          |}
          |
          |@textHtml = {
          |  @govukInput(Input(
          |    id = "contact-by-text",
          |    name = "contact-by-text",
          |    inputType = "tel",
          |    classes = "govuk-!-width-one-third",
          |    label = Label(
          |      content = Text(value = "Mobile phone number")
          |    )
          |  ))
          |}""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios6 " ignore {

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
          |      content = Text(value = "Filter"),
          |      classes = "govuk-fieldset__legend--m",
          |      isPageHeading = true
          |    ))
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Monthly"),
          |      value = Some("month")
          |    ),
          |    RadioItem(
          |      content = Text(value = "Yearly"),
          |      value = Some("year")
          |    )
          |  ),
          |  classes = "govuk-radios--small"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukRadios7 " ignore {

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
          |      content = Text(value = "Have you changed your name?"),
          |      classes = "govuk-fieldset__legend--xl",
          |      isPageHeading = true
          |    ))
          |  )),
          |  hint = Some(Hint(
          |    content = Text(value = "This includes changing your last name or spelling your name differently.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Select yes if you have changed your name")
          |  )),
          |  idPrefix = Some("changed-name"),
          |  name = "changed-name",
          |  items = Seq(
          |    RadioItem(
          |      content = Text(value = "Yes"),
          |      value = Some("yes")
          |    ),
          |    RadioItem(
          |      content = Text(value = "No"),
          |      value = Some("no")
          |    )
          |  ),
          |  classes = "govuk-radios--inline"
          |))""".stripMargin

      val gouvukRadiosParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukRadiosNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukRadiosParsed.get.value) shouldBe govukRadiosTwirlExpected
    }

    "format GovukSummaryList " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList1 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("name"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("date of birth"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact information"))
          |      )))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>")),
          |      actions = Some(Actions(items = Seq(
          |        ActionItem(href = "#", content = Text(value = "Change"), visuallyHiddenText = Some("contact details"))
          |      )))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList2 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>"))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukSummaryList3 " ignore {

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
        """@import uk.gov.hmrc.govukfrontend.views.html.components._
          |
          |@this(govukSummaryList: GovukSummaryList)
          |
          |@()
          |@govukSummaryList(SummaryList(
          |  classes = "govuk-summary-list--no-border",
          |  rows = Seq(
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Name")),
          |      value = Value(content = Text(value = "Sarah Philips"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Date of birth")),
          |      value = Value(content = Text(value = "5 January 1978"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact information")),
          |      value = Value(content = HtmlContent(value = "72 Guild Street<br>London<br>SE23 6FH"))
          |    ),
          |    SummaryListRow(
          |      key = Key(content = Text(value = "Contact details")),
          |      value = Value(content = HtmlContent(
          |        value = "<p class="govuk-body">07700 900457</p><p class="govuk-body">sarah.phillips@example.com</p>"))
          |    )
          |  )
          |))""".stripMargin

      val gouvukSummaryListParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukSummaryListNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukSummaryListParsed.get.value) shouldBe govukSummaryListTwirlExpected
    }

    "format GovukInput " ignore {

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
          |    content = Text(value = "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput1 " ignore {

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
          |    content = Text(value = "Event name")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput2 " ignore {

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
          |  classes = "govuk-input--width-20",
          |  label = Label(
          |    content = Text(value = "20 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-10",
          |  name = "width-10",
          |  classes = "govuk-input--width-10",
          |  label = Label(
          |    content = Text(value = "10 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-5",
          |  name = "width-5",
          |  classes = "govuk-input--width-5",
          |  label = Label(
          |    content = Text(value = "5 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-4",
          |  name = "width-4",
          |  classes = "govuk-input--width-4",
          |  label = Label(
          |    content = Text(value = "4 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-3",
          |  name = "width-3",
          |  classes = "govuk-input--width-3",
          |  label = Label(
          |    content = Text(value = "3 character width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "width-2",
          |  name = "width-2",
          |  classes = "govuk-input--width-2",
          |  label = Label(
          |    content = Text(value = "2 character width")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput3 " ignore {

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
          |  classes = "govuk-!-width-full",
          |  label = Label(
          |    content = Text(value = "Full width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "three-quarters",
          |  name = "three-quarters",
          |  classes = "govuk-!-width-three-quarters",
          |  label = Label(
          |    content = Text(value = "Three-quarters width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "two-thirds",
          |  name = "two-thirds",
          |  classes = "govuk-!-width-two-thirds",
          |  label = Label(
          |    content = Text(value = "Two-thirds width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-half",
          |  name = "one-half",
          |  classes = "govuk-!-width-one-half",
          |  label = Label(
          |    content = Text(value = "One-half width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-third",
          |  name = "one-third",
          |  classes = "govuk-!-width-one-third",
          |  label = Label(
          |    content = Text(value = "One-third width")
          |  )
          |))
          |@GovukInput(Input(
          |  id = "one-quarter",
          |  name = "one-quarter",
          |  classes = "govuk-!-width-one-quarter",
          |  label = Label(
          |    content = Text(value = "One-quarter width")
          |  )
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput4 " ignore {

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
          |    content = Text(value = "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "The name you’ll use on promotional material.")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukInput5 " ignore {

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
          |    content = Text(value = "Event name")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "The name you’ll use on promotional material.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Enter an event name")
          |  ))
          |))""".stripMargin

      val gouvukInputParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukInputNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukInputParsed.get.value) shouldBe govukInputTwirlExpected
    }

    "format GovukTextarea " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea1 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea2 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  rows = 8,
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

    "format GovukTextarea3 " ignore {

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
          |  name = "more-detail",
          |  id = "more-detail",
          |  label = Label(
          |    content = Text(value = "Can you provide more detail?")
          |  ),
          |  hint = Some(Hint(
          |    content = Text(value = "Do not include personal or financial information, like your National Insurance number or credit card details.")
          |  )),
          |  errorMessage = Some(ErrorMessage(
          |    content = Text(value = "Enter more detail")
          |  ))
          |))""".stripMargin

      val gouvukTextareaParsed: Parsed[NunjucksTemplate] =
        fastparse.parse(gouvukTextareaNunjucks, NunjucksParser.nunjucksParser(_))

      TwirlFormatter.format(gouvukTextareaParsed.get.value) shouldBe govukTextareaTwirlExpected
    }

  }

}
