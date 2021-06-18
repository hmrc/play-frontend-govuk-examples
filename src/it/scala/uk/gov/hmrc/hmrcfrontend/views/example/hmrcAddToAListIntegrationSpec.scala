package uk.gov.hmrc.hmrcfrontend.views
package example

import uk.gov.hmrc.govukfrontend.examples.HmrcFrontend
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcAddToAListIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(HmrcFrontend, "add-to-a-list", "addToSummary", addtoalistaddToSummary.f)
  testRendering(HmrcFrontend, "add-to-a-list", "changeList", addtoalistChangeList.f)
  testRendering(HmrcFrontend, "add-to-a-list", "checkAnswers", addtoalistCheckAnswers.f)
  testRendering(HmrcFrontend, "add-to-a-list", "removeFromList", addtoalistRemoveFromList.f)
  testRendering(HmrcFrontend, "add-to-a-list", "viewASummary", addtoalistViewASummary.f)
}
