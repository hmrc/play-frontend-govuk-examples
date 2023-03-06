package uk.gov.hmrc.govukfrontend.views
package examples

import uk.gov.hmrc.govukfrontend.examples.GovukFrontend
import uk.gov.hmrc.govukfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class govukPaginationIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(GovukFrontend, "pagination", "default", paginationDefault.f)
  testRendering(GovukFrontend, "pagination", "ellipses", paginationEllipses.f)
  testRendering(GovukFrontend, "pagination", "firstPage", paginationFirstPage.f)
  testRendering(GovukFrontend, "pagination", "inPage", paginationInPage.f)
  testRendering(GovukFrontend, "pagination", "labels", paginationLabels.f)
  testRendering(GovukFrontend, "pagination", "labels2", paginationLabels2.f)
  testRendering(GovukFrontend, "pagination", "largeNumberOfPages", paginationLargeNumberOfPages.f)
  testRendering(GovukFrontend, "pagination", "lastPage", paginationLastPage.f)
}
