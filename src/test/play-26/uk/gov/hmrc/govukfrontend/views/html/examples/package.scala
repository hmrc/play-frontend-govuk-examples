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

package uk.gov.hmrc.govukfrontend.views
package html

import uk.gov.hmrc.govukfrontend.views.html.components._

package object examples {

  lazy val BackLinkDefault = new backlink.default(GovukBackLink)

  lazy val ButtonDefault = new button.default(GovukButton)

  lazy val ButtonStart = new button.start(GovukButton)

  lazy val ButtonSecondary = new button.secondary(GovukButton)

  lazy val ButtonSecondaryCombo = new button.secondaryCombo(GovukButton)

  lazy val ButtonWarning = new button.warning(GovukButton)

  lazy val ButtonDisabled = new button.disabled(GovukButton)

  lazy val ButtonPreventDoubleClick = new button.preventDoubleClick(GovukButton)

  lazy val ErrorSummaryDefault = new errorsummary.default(GovukErrorSummary)

  lazy val ErrorSummaryLinking = new errorsummary.linking(GovukErrorSummary, GovukInput)

  lazy val ErrorSummaryLinkingMultipleFields = new errorsummary.linkingMultipleFields(GovukDateInput, GovukErrorSummary)

  lazy val ErrorSummaryLinkingCheckboxesRadios = new errorsummary.linkingCheckboxesRadios(GovukCheckboxes, GovukErrorSummary)

  lazy val ErrorSummaryFullPageExample =
    new errorsummary.fullPageExample(GovukBackLink, GovukButton, GovukDateInput, GovukErrorSummary)

  lazy val FieldsetAddressGroup = new fieldset.addressGroup(GovukFieldset, GovukInput)

  lazy val FieldsetDefault = new fieldset.default(GovukFieldset)

  lazy val ErrorMessageDefault = new errormessage.default(GovukDateInput)

  lazy val ErrorMessageLegend = new errormessage.legend(GovukCheckboxes)

  lazy val ErrorMessageLabel = new errormessage.label(GovukInput)

  lazy val FooterDefault = new footer.default(GovukFooter)

  lazy val FooterWithNavigation = new footer.withNavigation(GovukFooter)

  lazy val FooterWithMeta = new footer.withMeta(GovukFooter)

  lazy val FooterFull = new footer.full(GovukFooter)

  lazy val HeaderDefault = new header.default(GovukHeader)

  lazy val HeaderWithServiceName = new header.withServiceName(GovukHeader)

  lazy val HeaderWithServiceNameAndNavigation = new header.withServiceNameAndNavigation(GovukHeader)

  lazy val PanelDefault = new panel.default(GovukPanel)

  lazy val DetailsDefault = new details.default(GovukDetails)

  lazy val InputDefault = new textinput.default(GovukInput)

  lazy val InputFixedWidth = new textinput.inputFixedWidth(GovukInput)

  lazy val InputFluidWidth = new textinput.inputFluidWidth(GovukInput)

  lazy val InputHintText = new textinput.inputHintText(GovukInput)

  lazy val InputError = new textinput.error(GovukInput)

  lazy val TextareaDefault = new textarea.default(GovukTextarea)

  lazy val TextareaError = new textarea.error(GovukTextarea)

  lazy val TextareaSpecifyingRows = new textarea.specifyingRows(GovukTextarea)

  lazy val RadiosDefault = new radios.default(GovukRadios)

  lazy val RadiosConditionalReveal = new radios.conditionalReveal(GovukInput, GovukRadios)

  lazy val RadiosDivider = new radios.divider(GovukRadios)

  lazy val RadiosError = new radios.error(GovukRadios)

  lazy val RadiosHint = new radios.hint(GovukRadios)

  lazy val RadiosSmall = new radios.small(GovukRadios)

  lazy val RadiosStacked = new radios.stacked(GovukRadios)

  lazy val SummaryListDefault = new summarylist.default(GovukSummaryList)

  lazy val SummaryListWithoutActions = new summarylist.withoutActions(GovukSummaryList)

  lazy val SummaryListWithoutBorders = new summarylist.withoutBorders(GovukSummaryList)
}
