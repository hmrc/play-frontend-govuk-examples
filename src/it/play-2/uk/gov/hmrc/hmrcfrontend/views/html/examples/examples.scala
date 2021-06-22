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

  lazy val vatregistrationnumberHeading =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.heading])

  lazy val vatregistrationnumberLabel =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.label])

  lazy val vatregistrationnumberLabelError =
    Guice.createInjector().getInstance(classOf[vatregistrationnumber.labelError])
}
