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

package uk.gov.hmrc.hmrcfrontend.views
package html

import com.google.inject.Guice

package object examples {

  lazy val greenbuttonExample =
    Guice.createInjector().getInstance(classOf[greenbutton.example])

  lazy val accountmenuExample =
    Guice.createInjector().getInstance(classOf[accountmenu.example])

  lazy val accountsofficereferenceHeading =
    Guice.createInjector().getInstance(classOf[accountsofficereference.heading])

  lazy val accountsofficereferenceLabel =
    Guice.createInjector().getInstance(classOf[accountsofficereference.label])

  lazy val accountsofficereferenceLabelError =
    Guice.createInjector().getInstance(classOf[accountsofficereference.labelError])

  lazy val addtoalistAddToSummary =
    Guice.createInjector().getInstance(classOf[addtoalist.addToSummary])

  lazy val addtoalistChangeList =
    Guice.createInjector().getInstance(classOf[addtoalist.changeList])

  lazy val addtoalistCheckAnswers =
    Guice.createInjector().getInstance(classOf[addtoalist.checkAnswers])

  lazy val addtoalistRemoveFromList =
    Guice.createInjector().getInstance(classOf[addtoalist.removeFromList])

  lazy val addtoalistViewASummary =
    Guice.createInjector().getInstance(classOf[addtoalist.viewASummary])

  lazy val antiDumpingExample =
    Guice.createInjector().getInstance(classOf[antidumping.example1])

  lazy val asktheuserfortheirconsentMultipleCheckboxes =
    Guice.createInjector().getInstance(classOf[asktheuserfortheirconsent.multipleCheckboxes])

  lazy val asktheuserfortheirconsentSingleCheckbox =
    Guice.createInjector().getInstance(classOf[asktheuserfortheirconsent.singleCheckbox])

  lazy val asktheuserfortheirconsentYesNoQuestion =
    Guice.createInjector().getInstance(classOf[asktheuserfortheirconsent.yesNoQuestion])

  lazy val confirmedidentityExample =
    Guice.createInjector().getInstance(classOf[confirmedidentity.example])

  lazy val confirmedidentityExampleWelsh =
    Guice.createInjector().getInstance(classOf[confirmedidentity.exampleWelsh])

  lazy val couldnotconfirmidentityEndOfJourney =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.endOfJourney])

  lazy val couldnotconfirmidentityEndOfJourneyWelsh =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.endOfJourneyWelsh])

  lazy val couldnotconfirmidentityInsufficientInformation =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.insufficientInformation])

  lazy val couldnotconfirmidentityInsufficientInformationWelsh =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.insufficientInformationWelsh])

  lazy val couldnotconfirmidentityNoMatch =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.noMatch])

  lazy val couldnotconfirmidentityNoMatchWelsh =
    Guice.createInjector().getInstance(classOf[couldnotconfirmidentity.noMatchWelsh])

  lazy val employerpayereferenceHeading =
    Guice.createInjector().getInstance(classOf[employerpayereference.heading])

  lazy val employerpayereferenceLabel =
    Guice.createInjector().getInstance(classOf[employerpayereference.label])

  lazy val employerpayereferenceLabelError =
    Guice.createInjector().getInstance(classOf[employerpayereference.labelError])

  lazy val eorinumbersAsk =
    Guice.createInjector().getInstance(classOf[eorinumbers.ask])

  lazy val eorinumbersAskWelsh =
    Guice.createInjector().getInstance(classOf[eorinumbers.askWelsh])

  lazy val eorinumbersCheck =
    Guice.createInjector().getInstance(classOf[eorinumbers.check])

  lazy val eorinumbersCheckWelsh =
    Guice.createInjector().getInstance(classOf[eorinumbers.checkWelsh])

  lazy val eorinumbersError =
    Guice.createInjector().getInstance(classOf[eorinumbers.error])

  lazy val eorinumbersErrorWelsh =
    Guice.createInjector().getInstance(classOf[eorinumbers.errorWelsh])

  lazy val eorinumbersNotValid =
    Guice.createInjector().getInstance(classOf[eorinumbers.notValid])

  lazy val eorinumbersNotValidWelsh =
    Guice.createInjector().getInstance(classOf[eorinumbers.notValidWelsh])

  lazy val eorinumbersValid =
    Guice.createInjector().getInstance(classOf[eorinumbers.valid])

  lazy val eorinumbersValidWelsh =
    Guice.createInjector().getInstance(classOf[eorinumbers.validWelsh])

  lazy val extraStatutoryConcessionsExampleOne =
    Guice.createInjector().getInstance(classOf[extrastatutoryconcessions.example1])

  lazy val extraStatutoryConcessionsExampleTwo =
    Guice.createInjector().getInstance(classOf[extrastatutoryconcessions.example2])

  lazy val fileuploadAdditionalRow =
    Guice.createInjector().getInstance(classOf[fileupload.additionalRow])

  lazy val fileuploadErrors =
    Guice.createInjector().getInstance(classOf[fileupload.errors])

  lazy val fileuploadFixed =
    Guice.createInjector().getInstance(classOf[fileupload.fixed])

  lazy val fileuploadTypes =
    Guice.createInjector().getInstance(classOf[fileupload.types])

  lazy val fileuploadQuestion =
    Guice.createInjector().getInstance(classOf[fileupload.question])

  lazy val fileuploadUploadAdditionalFiles =
    Guice.createInjector().getInstance(classOf[fileupload.uploadAdditionalFiles])

  lazy val fileuploadUploadASingleFile =
    Guice.createInjector().getInstance(classOf[fileupload.uploadASingleFile])

  lazy val fileuploadUploaded =
    Guice.createInjector().getInstance(classOf[fileupload.uploaded])

  lazy val fileuploadUploading =
    Guice.createInjector().getInstance(classOf[fileupload.uploading])

  lazy val forceoflawExampleOne =
    Guice.createInjector().getInstance(classOf[forceoflaw.example1])

  lazy val forceoflawExampleTwo =
    Guice.createInjector().getInstance(classOf[forceoflaw.example2])

  lazy val forceoflawExampleThree =
    Guice.createInjector().getInstance(classOf[forceoflaw.example3])

  lazy val forceoflawExampleFour =
    Guice.createInjector().getInstance(classOf[forceoflaw.example4])

  lazy val forceoflawExampleFive =
    Guice.createInjector().getInstance(classOf[forceoflaw.example5])

  lazy val forceoflawExampleSix =
    Guice.createInjector().getInstance(classOf[forceoflaw.example6])

  lazy val governmentGatewayOptionOne =
    Guice.createInjector().getInstance(classOf[governmentgateway.option1])

  lazy val governmentGatewayOptionTwo =
    Guice.createInjector().getInstance(classOf[governmentgateway.option2])

  lazy val governmentGatewayOptionThree =
    Guice.createInjector().getInstance(classOf[governmentgateway.option3])

  lazy val hidinginformationExampleOne =
    Guice.createInjector().getInstance(classOf[hidinginformation.example1])

  lazy val hidinginformationExampleOneWelsh =
    Guice.createInjector().getInstance(classOf[hidinginformation.example1Welsh])

  lazy val hidinginformationExampleTwo =
    Guice.createInjector().getInstance(classOf[hidinginformation.example2])

  lazy val hidinginformationExampleTwoWelsh =
    Guice.createInjector().getInstance(classOf[hidinginformation.example2Welsh])

  lazy val hidinginformationExampleThree =
    Guice.createInjector().getInstance(classOf[hidinginformation.example3])

  lazy val hidinginformationExampleThreeWelsh =
    Guice.createInjector().getInstance(classOf[hidinginformation.example3Welsh])

  lazy val matchanorganistionCompanyRegNumber =
    Guice.createInjector().getInstance(classOf[matchanorganisationtoHMRCrecords.companyRegNumber])

  lazy val notificationbadgeNoItems =
    Guice.createInjector().getInstance(classOf[notificationbadge.noItems])

  lazy val notificationbadgeWithItems =
    Guice.createInjector().getInstance(classOf[notificationbadge.withItems])

  lazy val openlinksinanewwindowortabExample =
    Guice.createInjector().getInstance(classOf[openlinksinanewwindowortab.example])

  lazy val openlinksinanewwindowortabExampleWelsh =
    Guice.createInjector().getInstance(classOf[openlinksinanewwindowortab.exampleWelsh])

  lazy val pageheadingWithoutSubheading =
    Guice.createInjector().getInstance(classOf[pageheading.withoutSubheading])

  lazy val pageheadingWithSubheading =
    Guice.createInjector().getInstance(classOf[pageheading.withSubheading])

  lazy val pageheadingAsLabelWithSubheading =
    Guice.createInjector().getInstance(classOf[pageheading.headingAsLabelWithSubheading])

  lazy val pageheadingAsLabelWithSubheadingWelsh =
    Guice.createInjector().getInstance(classOf[pageheading.headingAsLabelWithSubheadingWelsh])

  lazy val pageheadingLegendsAsPageHeadingsWithSubheading =
    Guice.createInjector().getInstance(classOf[pageheading.legendsAsPageHeadingsWithSubheading])

  lazy val pageheadingLegendsAsPageHeadingsWithSubheadingWelsh =
    Guice.createInjector().getInstance(classOf[pageheading.legendsAsPageHeadingsWithSubheadingWelsh])

  lazy val pageheadingLegendsAsPageHeadingsWithSubheadingWelshWithError =
    Guice.createInjector().getInstance(classOf[pageheading.legendsAsPageHeadingsWithSubheadingWelshWithError])

  lazy val pageheadingLegendsAsPageHeadingsWithSubheadingWithError =
    Guice.createInjector().getInstance(classOf[pageheading.legendsAsPageHeadingsWithSubheadingWithError])

  lazy val pagenotfoundExample =
    Guice.createInjector().getInstance(classOf[pagenotfound.example])

  lazy val pagenotfoundExampleWelsh =
    Guice.createInjector().getInstance(classOf[pagenotfound.exampleWelsh])

  lazy val registerforataxExample =
    Guice.createInjector().getInstance(classOf[registerforatax.example])

  lazy val serviceunavailableGeneralPage =
    Guice.createInjector().getInstance(classOf[serviceunavailable.generalPage])

  lazy val serviceunavailableGeneralPageWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.generalPageWelsh])

  lazy val serviceunavailableLinkToOtherService =
    Guice.createInjector().getInstance(classOf[serviceunavailable.linkToOtherService])

  lazy val serviceunavailableLinkToOtherServiceWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.linkToOtherServiceWelsh])

  lazy val serviceunavailableServiceClosed =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceClosed])

  lazy val serviceunavailableServiceClosedWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceClosedWelsh])

  lazy val serviceunavailableServiceNotYetOpen =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceNotYetOpen])

  lazy val serviceunavailableServiceNotYetOpenWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceNotYetOpenWelsh])

  lazy val serviceunavailableServiceReplaced =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceReplaced])

  lazy val serviceunavailableServiceReplacedWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceReplacedWelsh])

  lazy val serviceunavailableServiceShutDown =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceShutDown])

  lazy val serviceunavailableServiceShutDownWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceShutDownWelsh])

  lazy val serviceunavailableServiceTemporarilyUnavailable =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceTemporarilyUnavailable])

  lazy val serviceunavailableServiceTemporarilyUnavailableWelsh =
    Guice.createInjector().getInstance(classOf[serviceunavailable.serviceTemporarilyUnavailableWelsh])

  lazy val signoutExample =
    Guice.createInjector().getInstance(classOf[signout.example])

  lazy val statustagsintasklistpagesExample =
    Guice.createInjector().getInstance(classOf[statustagsintasklistpages.example])

  lazy val statustagsintasklistpagesExampleWelsh =
    Guice.createInjector().getInstance(classOf[statustagsintasklistpages.exampleWelsh])

  lazy val thereisaproblemwiththeserviceLinkToOtherService =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.linkToOtherService])

  lazy val thereisaproblemwiththeserviceLinkToOtherServiceWelsh =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.linkToOtherServiceWelsh])

  lazy val thereisaproblemwiththeserviceNoContact =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.noContact])

  lazy val thereisaproblemwiththeserviceNoContactWelsh =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.noContactWelsh])

  lazy val thereisaproblemwiththeserviceProblemWithService =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.problemWithService])

  lazy val thereisaproblemwiththeserviceProblemWithServiceWelsh =
    Guice.createInjector().getInstance(classOf[thereisaproblemwiththeservice.problemWithServiceWelsh])

  lazy val uniquetaxpayerreferenceCorporationTax =
    Guice.createInjector().getInstance(classOf[uniquetaxpayerreference.corporationTax])

  lazy val uniquetaxpayerreferenceLabel =
    Guice.createInjector().getInstance(classOf[uniquetaxpayerreference.label])

  lazy val uniquetaxpayerreferenceLabelError =
    Guice.createInjector().getInstance(classOf[uniquetaxpayerreference.labelError])

  lazy val uniquetaxpayerreferenceSelfAssessment =
    Guice.createInjector().getInstance(classOf[uniquetaxpayerreference.selfAssessment])

  lazy val researchbannerExampleLinear =
    Guice.createInjector().getInstance(classOf[researchbanner.exampleLinear])

  lazy val researchbannerExampleNonLinear =
    Guice.createInjector().getInstance(classOf[researchbanner.exampleNonLinear])

  lazy val vatregistrationnumberHeading =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.heading])

  lazy val vatregistrationnumberLabel =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.label])

  lazy val vatregistrationnumberLabelError =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.labelError])
}
