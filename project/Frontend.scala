sealed trait Frontend {
  val exampleIdPrefix: String
  val templateServiceSubpath: String
  val twirlToTemplateServiceExampleIdMap: Map[String, String]
  val twirlSubpath: String

  def toTemplateServiceExampleId(twirlExampleId: String): String = {
    val TwirlReg = s"^($exampleIdPrefix)?([^/]*)(/?.*)$$".r
    twirlExampleId match {
      case njksId @ TwirlReg(_, twirl, suffix) =>
        twirlToTemplateServiceExampleIdMap
          .get(twirl)
          .map(njks => s"$njks$suffix")
          .getOrElse(njksId)
      case njksId                              => njksId
    }
  }
}

object Frontend {

  case object GovukFrontend extends Frontend {
    val exampleIdPrefix        = ""
    val templateServiceSubpath = "govuk"
    val twirlSubpath           = "govukfrontend"

    val twirlToTemplateServiceExampleIdMap: Map[String, String] = Map(
      "backlink"       -> "backLink",
      "charactercount" -> "characterCount",
      "dateinput"      -> "dateInput",
      "errormessage"   -> "errorMessage",
      "errorsummary"   -> "errorSummary",
      "fileupload"     -> "fileUpload",
      "insettext"      -> "insetText",
      "phasebanner"    -> "phaseBanner",
      "skiplink"       -> "skipLink",
      "summarylist"    -> "summaryList",
      "textinput"      -> "input",
      "warningtext"    -> "warningText"
    )

  }

  case object HmrcFrontend extends Frontend {
    val exampleIdPrefix                                         = "hmrc/"
    val templateServiceSubpath                                  = "hmrc"
    val twirlSubpath                                            = "hmrcfrontend"
    val twirlToTemplateServiceExampleIdMap: Map[String, String] = Map(
      "accountheader"                 -> "accountHeader",
      "accountmenu"                   -> "accountMenu",
      "accountsofficereference"       -> "accountsOfficeReference",
      "addtoalist"                    -> "addToAList",
      "asktheuserfortheirconsent"     -> "askTheUserForTheirConsent",
      "currencyinput"                 -> "currencyInput",
      "confirmedidentity"             -> "confirmedIdentity",
      "couldnotconfirmidentity"       -> "couldNotConfirmIdentity",
      "employerpayereference"         -> "employerPayeReference",
      "greenbutton"                   -> "greenButton",
      "internalheader"                -> "internalHeader",
      "languageselect"                -> "languageSelect",
      "newtablink"                    -> "newTabLink",
      "notificationbadge"             -> "notificationBadge",
      "openlinksinanewwindowortab"    -> "openLinksInANewWindowOrTab",
      "pageheading"                   -> "pageHeading",
      "pagenotfound"                  -> "pageNotFound",
      "registerforatax"               -> "registerForATax",
      "serviceunavailable"            -> "serviceUnavailable",
      "signout"                       -> "signOut",
      "statustagsintasklistpages"     -> "statusTagsInTaskListPages",
      "thereisaproblemwiththeservice" -> "thereIsAProblemWithTheService",
      "uniquetaxpayerreference"       -> "uniqueTaxpayerReference",
      "vatregistrationnumber"         -> "vatRegistrationNumber"
    )
  }

}
