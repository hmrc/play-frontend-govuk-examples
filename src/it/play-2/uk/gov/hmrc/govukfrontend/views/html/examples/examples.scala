/*
 * Copyright 2023 HM Revenue & Customs
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

import com.google.inject.Guice

package object examples {

  lazy val accordionDefault =
    Guice.createInjector().getInstance(classOf[accordion.default])

  lazy val accordionWithSummarySection =
    Guice.createInjector().getInstance(classOf[accordion.withSummarySection])

  lazy val backlinkDefault =
    Guice.createInjector().getInstance(classOf[backlink.default])

  lazy val breadcrumbsDefault =
    Guice.createInjector().getInstance(classOf[breadcrumbs.default])

  lazy val buttonDefault =
    Guice.createInjector().getInstance(classOf[button.default])

  lazy val buttonDisabled =
    Guice.createInjector().getInstance(classOf[button.disabled])

  lazy val buttonPreventDoubleClick =
    Guice.createInjector().getInstance(classOf[button.preventDoubleClick])

  lazy val buttonSecondary =
    Guice.createInjector().getInstance(classOf[button.secondary])

  lazy val buttonSecondaryCombo =
    Guice.createInjector().getInstance(classOf[button.secondaryCombo])

  lazy val buttonStart =
    Guice.createInjector().getInstance(classOf[button.start])

  lazy val buttonWarning =
    Guice.createInjector().getInstance(classOf[button.warning])

  lazy val charactercountDefault =
    Guice.createInjector().getInstance(classOf[charactercount.default])

  lazy val charactercountError =
    Guice.createInjector().getInstance(classOf[charactercount.error])

  lazy val charactercountWithoutHeading =
    Guice.createInjector().getInstance(classOf[charactercount.withoutHeading])

  lazy val charactercountThreshold =
    Guice.createInjector().getInstance(classOf[charactercount.threshold])

  lazy val charactercountWordCount =
    Guice.createInjector().getInstance(classOf[charactercount.wordCount])

  lazy val checkboxesDefault =
    Guice.createInjector().getInstance(classOf[checkboxes.default])

  lazy val checkboxesConditionalReveal =
    Guice.createInjector().getInstance(classOf[checkboxes.conditionalReveal])

  lazy val checkboxesError =
    Guice.createInjector().getInstance(classOf[checkboxes.error])

  lazy val checkboxesHint =
    Guice.createInjector().getInstance(classOf[checkboxes.hint])

  lazy val checkboxesSmall =
    Guice.createInjector().getInstance(classOf[checkboxes.small])

  lazy val dateinputDefault =
    Guice.createInjector().getInstance(classOf[dateinput.default])

  lazy val dateinputDateOfBirth =
    Guice.createInjector().getInstance(classOf[dateinput.dateOfBirth])

  lazy val dateinputError =
    Guice.createInjector().getInstance(classOf[dateinput.error])

  lazy val detailsDefault      =
    Guice.createInjector().getInstance(classOf[details.default])
  lazy val errormessageDefault =
    Guice.createInjector().getInstance(classOf[errormessage.default])

  lazy val errormessageCustomPrefix =
    Guice.createInjector().getInstance(classOf[errormessage.customPrefix])

  lazy val errormessageLabel =
    Guice.createInjector().getInstance(classOf[errormessage.label])

  lazy val errormessageLegend =
    Guice.createInjector().getInstance(classOf[errormessage.legend])

  lazy val errorsummaryDefault =
    Guice.createInjector().getInstance(classOf[errorsummary.default])

  lazy val errorsummaryLinking =
    Guice.createInjector().getInstance(classOf[errorsummary.linking])

  lazy val errorsummaryLinkingCheckboxesRadios =
    Guice.createInjector().getInstance(classOf[errorsummary.linkingCheckboxesRadios])

  lazy val errorsummaryLinkingMultipleFields =
    Guice.createInjector().getInstance(classOf[errorsummary.linkingMultipleFields])

  lazy val exitthispageDefault =
    Guice.createInjector().getInstance(classOf[exitthispage.default])

  lazy val exitthispageSecondaryLink =
    Guice.createInjector().getInstance(classOf[exitthispage.secondaryLink])

  lazy val fieldsetDefault =
    Guice.createInjector().getInstance(classOf[fieldset.default])

  lazy val fieldsetAddressGroup =
    Guice.createInjector().getInstance(classOf[fieldset.addressGroup])

  lazy val fileuploadDefault =
    Guice.createInjector().getInstance(classOf[fileupload.default])

  lazy val fileuploadError =
    Guice.createInjector().getInstance(classOf[fileupload.error])

  lazy val footerDefault =
    Guice.createInjector().getInstance(classOf[footer.default])

  lazy val footerFull =
    Guice.createInjector().getInstance(classOf[footer.full])

  lazy val footerWithMeta =
    Guice.createInjector().getInstance(classOf[footer.withMeta])

  lazy val footerWithNavigation =
    Guice.createInjector().getInstance(classOf[footer.withNavigation])

  lazy val headerDefault =
    Guice.createInjector().getInstance(classOf[header.default])

  lazy val headerWithServiceName =
    Guice.createInjector().getInstance(classOf[header.withServiceName])

  lazy val headerWithServiceNameAndNavigation =
    Guice.createInjector().getInstance(classOf[header.withServiceNameAndNavigation])

  lazy val insettextDefault =
    Guice.createInjector().getInstance(classOf[insettext.default])

  lazy val notificationbannerDefault =
    Guice.createInjector().getInstance(classOf[notificationbanner.default])

  lazy val notificationbannerSuccess =
    Guice.createInjector().getInstance(classOf[notificationbanner.success])

  lazy val notificationbannerWholeService =
    Guice.createInjector().getInstance(classOf[notificationbanner.wholeService])

  lazy val paginationDefault =
    Guice.createInjector().getInstance(classOf[pagination.default])

  lazy val paginationEllipses =
    Guice.createInjector().getInstance(classOf[pagination.ellipses])

  lazy val paginationFirstPage =
    Guice.createInjector().getInstance(classOf[pagination.firstPage])

  lazy val paginationInPage =
    Guice.createInjector().getInstance(classOf[pagination.inPage])

  lazy val paginationLabels =
    Guice.createInjector().getInstance(classOf[pagination.labels])

  lazy val paginationLabels2 =
    Guice.createInjector().getInstance(classOf[pagination.labels2])

  lazy val paginationLargeNumberOfPages =
    Guice.createInjector().getInstance(classOf[pagination.largeNumberOfPages])

  lazy val paginationLastPage =
    Guice.createInjector().getInstance(classOf[pagination.lastPage])

  lazy val panelDefault =
    Guice.createInjector().getInstance(classOf[panel.default])

  lazy val phasebannerDefault =
    Guice.createInjector().getInstance(classOf[phasebanner.default])

  lazy val phasebannerBeta =
    Guice.createInjector().getInstance(classOf[phasebanner.beta])

  lazy val radiosDefault =
    Guice.createInjector().getInstance(classOf[radios.default])

  lazy val radiosConditionalReveal =
    Guice.createInjector().getInstance(classOf[radios.conditionalReveal])

  lazy val radiosConditionalRevealError =
    Guice.createInjector().getInstance(classOf[radios.conditionalRevealError])

  lazy val radiosDivider =
    Guice.createInjector().getInstance(classOf[radios.divider])

  lazy val radiosError =
    Guice.createInjector().getInstance(classOf[radios.error])

  lazy val radiosHint =
    Guice.createInjector().getInstance(classOf[radios.hint])

  lazy val radiosSmall =
    Guice.createInjector().getInstance(classOf[radios.small])

  lazy val radiosInline =
    Guice.createInjector().getInstance(classOf[radios.inline])

  lazy val radiosWithoutHeading =
    Guice.createInjector().getInstance(classOf[radios.withoutHeading])

  lazy val selectDefault =
    Guice.createInjector().getInstance(classOf[select.default])

  lazy val skiplinkDefault =
    Guice.createInjector().getInstance(classOf[skiplink.default])

  lazy val summarylistDefault =
    Guice.createInjector().getInstance(classOf[summarylist.default])

  lazy val summarylistWithoutActions =
    Guice.createInjector().getInstance(classOf[summarylist.withoutActions])

  lazy val summarylistWithoutBorders =
    Guice.createInjector().getInstance(classOf[summarylist.withoutBorders])

  lazy val summarylistMixedActions =
    Guice.createInjector().getInstance(classOf[summarylist.mixedActions])

  lazy val summarylistCardWithActions =
    Guice.createInjector().getInstance(classOf[summarylist.cardWithActions])

  lazy val summarylistCardWithTitle =
    Guice.createInjector().getInstance(classOf[summarylist.cardWithTitle])

  lazy val tableDefault =
    Guice.createInjector().getInstance(classOf[table.default])

  lazy val tableColumnWidths =
    Guice.createInjector().getInstance(classOf[table.columnWidths])

  lazy val tableColumnWidthsCustomClasses =
    Guice.createInjector().getInstance(classOf[table.columnWidthsCustomClasses])

  lazy val tableNumbers =
    Guice.createInjector().getInstance(classOf[table.numbers])

  lazy val tabsDefault =
    Guice.createInjector().getInstance(classOf[tabs.default])

  lazy val tagDefault =
    Guice.createInjector().getInstance(classOf[tag.default])

  lazy val textareaDefault =
    Guice.createInjector().getInstance(classOf[textarea.default])

  lazy val textareaError =
    Guice.createInjector().getInstance(classOf[textarea.error])

  lazy val textareaSpecifyingRows =
    Guice.createInjector().getInstance(classOf[textarea.specifyingRows])

  lazy val textinputDefault =
    Guice.createInjector().getInstance(classOf[textinput.default])

  lazy val textinputError =
    Guice.createInjector().getInstance(classOf[textinput.error])

  lazy val textinputInputAutocompleteAttribute =
    Guice.createInjector().getInstance(classOf[textinput.inputAutocompleteAttribute])

  lazy val textinputInputFixedWidth =
    Guice.createInjector().getInstance(classOf[textinput.inputFixedWidth])

  lazy val textinputInputFluidWidth =
    Guice.createInjector().getInstance(classOf[textinput.inputFluidWidth])

  lazy val textinputInputHintText =
    Guice.createInjector().getInstance(classOf[textinput.inputHintText])

  lazy val textinputInputSpellcheckDisabled =
    Guice.createInjector().getInstance(classOf[textinput.inputSpellcheckDisabled])

  lazy val warningtextDefault =
    Guice.createInjector().getInstance(classOf[warningtext.default])
}
