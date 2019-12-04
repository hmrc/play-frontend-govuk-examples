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
import fastparse._
import org.scalatest.{Matchers, WordSpec}
import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.examples.NunjucksParser._
import uk.gov.hmrc.govukfrontend.views.html.components._

import scala.collection.mutable.ArrayBuffer

class NunjucksParserSpec extends WordSpec with Matchers {

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
        "abc",
        5
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

  "imports parser" should {
    "parse" in {
      val s =
        """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}
          |{% from "govuk/components/date-input/macro.njk" import govukDateInput %}
          |""".stripMargin

      fastparse.parse(s, imports(_)) shouldBe Success(
        ArrayBuffer(
          Import("govuk/components/error-summary/macro.njk", "govukErrorSummary"),
          Import("govuk/components/date-input/macro.njk", "govukDateInput")
        ),
        152
      )
    }
  }

  "macroName parser" should {
    "parse" in {
      val s = """govukErrorSummary({"""

      fastparse.parse(s, macroName(_)) shouldBe Success(
        "govukErrorSummary",
        17
      )
    }
  }

  "macroCall parser" should {
    "parse" in {
      val s =
        """{{ govukErrorSummary({
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

      fastparse.parse(s, macroCall()(_)) shouldBe Success(
        MacroCall(
          macroName = "govukErrorSummary",
          args = ErrorSummary(
            title = Text("There is a problem"),
            errorList = Seq(
              ErrorLink(
                content = Text("The date your passport was issued must be in the past"),
                href    = Some("#passport-issued-error")
              ),
              ErrorLink(
                content = Text("Enter a postcode, like AA1 1AA"),
                href    = Some("#postcode-error")
              )
            )
          )
        ),
        289
      )
    }
  }

  "nunjucksParser" should {
    "parse error summary example" in {
      val s =
        """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}
          |
          |<div class="govuk-grid-row">
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
          |}) }}
          |
          |</div>
          |""".stripMargin

      fastparse.parse(s, nunjucksParser(_)) shouldBe Success(
        NunjucksTemplate(
          imports = List(
            Import(from = "govuk/components/error-summary/macro.njk", macroName = "govukErrorSummary")
          ),
          body = List(
            TemplateHtml(Html("""<div class="govuk-grid-row">
           |
           |""".stripMargin)),
            MacroCall(
              macroName = "govukErrorSummary",
              args = ErrorSummary(
                title = Text("There is a problem"),
                errorList = Seq(
                  ErrorLink(
                    content = Text("The date your passport was issued must be in the past"),
                    href    = Some("#passport-issued-error")
                  ),
                  ErrorLink(
                    content = Text("Enter a postcode, like AA1 1AA"),
                    href    = Some("#postcode-error")
                  )
                )
              )
            ),
            TemplateHtml(Html("""</div>
      |""".stripMargin))
          )
        ),
        408
      )
    }

    "parse linking checkboxes radios example" in {
      val s =
        """{% from "govuk/components/error-summary/macro.njk" import govukErrorSummary %}
          |{% from "govuk/components/checkboxes/macro.njk" import govukCheckboxes %}
          |
          |{{ govukErrorSummary({
          |  "titleText": "There is a problem",
          |  "errorList": [
          |    {
          |      "text": "Select if you are British, Irish or a citizen of a different country",
          |      "href": "#nationality"
          |    }
          |  ]
          |}) }}
          |
          |{{ govukCheckboxes({
          |  idPrefix: "nationality",
          |  name: "nationality",
          |  fieldset: {
          |    legend: {
          |      text: "What is your nationality?",
          |      isPageHeading: true,
          |      classes: "govuk-fieldset__legend--xl"
          |    }
          |  },
          |  hint: {
          |    text: "If you have dual nationality, select all options that are relevant to you."
          |  },
          |  errorMessage: {
          |    text: "Select if you are British, Irish or a citizen of a different country"
          |  },
          |  items: [
          |    {
          |      value: "british",
          |      text: "British",
          |      hint: {
          |        text: "including English, Scottish, Welsh and Northern Irish"
          |      },
          |      id: "nationality"
          |    },
          |    {
          |      value: "irish",
          |      text: "Irish"
          |    },
          |    {
          |      value: "other",
          |      text: "Citizen of another country"
          |    }
          |  ]
          |}) }}""".stripMargin

      fastparse.parse(s, nunjucksParser(_)) shouldBe Success(
        NunjucksTemplate(
          imports = List(
            Import(from = "govuk/components/error-summary/macro.njk", macroName = "govukErrorSummary"),
            Import(from = "govuk/components/checkboxes/macro.njk", macroName    = "govukCheckboxes")
          ),
          body = List(
            MacroCall(
              macroName = "govukErrorSummary",
              args = ErrorSummary(
                errorList = Seq(
                  ErrorLink(
                    href    = Some("#nationality"),
                    content = Text("Select if you are British, Irish or a citizen of a different country")
                  )
                ),
                title = Text("There is a problem")
              )
            ),
            MacroCall(
              macroName = "govukCheckboxes",
              args = Checkboxes(
                idPrefix = Some("nationality"),
                name     = "nationality",
                fieldset = Some(
                  Fieldset(
                    legend = Some(
                      Legend(
                        content       = Text("What is your nationality?"),
                        isPageHeading = true,
                        classes       = "govuk-fieldset__legend--xl"
                      ))
                  )),
                hint = Some(
                  Hint(
                    content = Text("If you have dual nationality, select all options that are relevant to you.")
                  )),
                errorMessage = Some(
                  ErrorMessage(
                    content = Text("Select if you are British, Irish or a citizen of a different country")
                  )),
                items = Seq(
                  CheckboxItem(
                    value   = "british",
                    content = Text("British"),
                    hint    = Some(Hint(content = Text("including English, Scottish, Welsh and Northern Irish"))),
                    id      = Some("nationality")
                  ),
                  CheckboxItem(
                    value   = "irish",
                    content = Text("Irish")
                  ),
                  CheckboxItem(
                    value   = "other",
                    content = Text("Citizen of another country")
                  )
                )
              )
            )
          )
        ),
        1124
      )
    }

    "parse footer full example" in {
      val s =
        """{% from "govuk/components/footer/macro.njk" import govukFooter %}
          |
          |{{ govukFooter({
          |  navigation: [
          |    {
          |      title: "Services and information",
          |      columns: 2,
          |      items: [
          |        {
          |          href: "#",
          |          text: "Benefits"
          |        },
          |        {
          |          href: "#",
          |          text: "Births, deaths, marriages and care"
          |        },
          |        {
          |          href: "#",
          |          text: "Business and self-employed"
          |        },
          |        {
          |          href: "#",
          |          text: "Childcare and parenting"
          |        },
          |        {
          |          href: "#",
          |          text: "Citizenship and living in the UK"
          |        },
          |        {
          |          href: "#",
          |          text: "Crime, justice and the law"
          |        },
          |        {
          |          href: "#",
          |          text: "Disabled people"
          |        },
          |        {
          |          href: "#",
          |          text: "Driving and transport"
          |        },
          |        {
          |          href: "#",
          |          text: "Education and learning"
          |        },
          |        {
          |          href: "#",
          |          text: "Employing people"
          |        },
          |        {
          |          href: "#",
          |          text: "Environment and countryside"
          |        },
          |        {
          |          href: "#",
          |          text: "Housing and local services"
          |        },
          |        {
          |          href: "#",
          |          text: "Money and tax"
          |        },
          |        {
          |          href: "#",
          |          text: "Passports, travel and living abroad"
          |        },
          |        {
          |          href: "#",
          |          text: "Visas and immigration"
          |        },
          |        {
          |          href: "#",
          |          text: "Working, jobs and pensions"
          |        }
          |      ]
          |    },
          |    {
          |      title: "Departments and policy",
          |      items: [
          |        {
          |          href: "#",
          |          text: "How government works"
          |        },
          |        {
          |          href: "#",
          |          text: "Departments"
          |        },
          |        {
          |          href: "#",
          |          text: "Worldwide"
          |        },
          |        {
          |          href: "#",
          |          text: "Policies"
          |        },
          |        {
          |          href: "#",
          |          text: "Publications"
          |        },
          |        {
          |          href: "#",
          |          text: "Announcements"
          |        }
          |      ]
          |    }
          |  ],
          |  meta: {
          |    items: [
          |      {
          |        href: "#",
          |        text: "Help"
          |      },
          |      {
          |        href: "#",
          |        text: "Cookies"
          |      },
          |      {
          |        href: "#",
          |        text: "Contact"
          |      },
          |      {
          |        href: "#",
          |        text: "Terms and conditions"
          |      },
          |      {
          |        href: "#",
          |        text: "Rhestr o Wasanaethau Cymraeg"
          |      }
          |    ],
          |    html: 'Built by the <a href="#" class="govuk-footer__link">Government Digital Service</a>'
          |  }
          |}) }}""".stripMargin

      fastparse.parse(s, nunjucksParser(_)) shouldBe Success(
        NunjucksTemplate(
          imports = List(
            Import(from = "govuk/components/footer/macro.njk", macroName = "govukFooter")
          ),
          body = List(
            MacroCall(
              macroName = "govukFooter",
              args = Footer(
                navigation = Some(Seq(
                  FooterNavigation(
                    title   = Some("Services and information"),
                    columns = Some(2),
                    items = Some(Seq(
                      FooterItem(href = Some("#"), text = Some("Benefits")),
                      FooterItem(href = Some("#"), text = Some("Births, deaths, marriages and care")),
                      FooterItem(href = Some("#"), text = Some("Business and self-employed")),
                      FooterItem(href = Some("#"), text = Some("Childcare and parenting")),
                      FooterItem(href = Some("#"), text = Some("Citizenship and living in the UK")),
                      FooterItem(href = Some("#"), text = Some("Crime, justice and the law")),
                      FooterItem(href = Some("#"), text = Some("Disabled people")),
                      FooterItem(href = Some("#"), text = Some("Driving and transport")),
                      FooterItem(href = Some("#"), text = Some("Education and learning")),
                      FooterItem(href = Some("#"), text = Some("Employing people")),
                      FooterItem(href = Some("#"), text = Some("Environment and countryside")),
                      FooterItem(href = Some("#"), text = Some("Housing and local services")),
                      FooterItem(href = Some("#"), text = Some("Money and tax")),
                      FooterItem(href = Some("#"), text = Some("Passports, travel and living abroad")),
                      FooterItem(href = Some("#"), text = Some("Visas and immigration")),
                      FooterItem(href = Some("#"), text = Some("Working, jobs and pensions"))
                    ))
                  ),
                  FooterNavigation(
                    title = Some("Departments and policy"),
                    items = Some(Seq(
                      FooterItem(href = Some("#"), text = Some("How government works")),
                      FooterItem(href = Some("#"), text = Some("Departments")),
                      FooterItem(href = Some("#"), text = Some("Worldwide")),
                      FooterItem(href = Some("#"), text = Some("Policies")),
                      FooterItem(href = Some("#"), text = Some("Publications")),
                      FooterItem(href = Some("#"), text = Some("Announcements"))
                    ))
                  )
                )),
                meta = Some(Meta(
                  items = Some(Seq(
                    FooterItem(href = Some("#"), text = Some("Help")),
                    FooterItem(href = Some("#"), text = Some("Cookies")),
                    FooterItem(href = Some("#"), text = Some("Contact")),
                    FooterItem(href = Some("#"), text = Some("Terms and conditions")),
                    FooterItem(href = Some("#"), text = Some("Rhestr o Wasanaethau Cymraeg"))
                  )),
                  content = HtmlContent(
                    """Built by the <a href="#" class="govuk-footer__link">Government Digital Service</a>""")
                ))
              )
            )
          )
        ),
        2522
      )

    }

    "parse fieldset full example" in {
      val s =
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

      fastparse.parse(s, nunjucksParser(_)) shouldBe Success(
        NunjucksTemplate(
          imports = List(
            Import(from = "govuk/components/footer/macro.njk", macroName = "govukInput"),
            Import(from = "govuk/components/footer/macro.njk", macroName = "govukFieldset")
          ),
          body = List(
            CallMacro(
              callMacro = MacroCall(
                "GovukFieldset",
                Fieldset(
                  legend = Some(
                    Legend(
                      content       = Text("What is your address?"),
                      classes       = "govuk-fieldset__legend--xl",
                      isPageHeading = true
                    ))
                )),
              macroCalls = List(
                MacroCall(
                  "GovukInput",
                  Input(
                    id   = "address-line-1",
                    name = "address-line-1",
                    label = Label(
                      content =
                        HtmlContent("""Building and street <span class="govuk-visually-hidden">line 1 of 2</span>""")
                    )
                  )
                ),
                MacroCall(
                  "GovukInput",
                  Input(
                    id   = "address-line-2",
                    name = "address-line-2",
                    label = Label(
                      content =
                        HtmlContent("""<span class="govuk-visually-hidden">Building and street line 2 of 2</span>""")
                    )
                  )
                ),
                MacroCall(
                  "GovukInput",
                  Input(
                    id   = "address-town",
                    name = "address-town",
                    label = Label(
                      content = Text("Town or city")
                    ),
                    classes = "govuk-!-width-two-thirds"
                  )),
                MacroCall(
                  "GovukInput",
                  Input(
                    id   = "address-county",
                    name = "address-county",
                    label = Label(
                      content = Text("County")
                    ),
                    classes = "govuk-!-width-two-thirds"
                  )),
                MacroCall(
                  "GovukInput",
                  Input(
                    id   = "address-postcode",
                    name = "address-postcode",
                    label = Label(
                      content = Text("Postcode")
                    ),
                    classes = "govuk-input--width-10"
                  ))
              )
            ))
        ),
        1166
      )

    }
  }
}
