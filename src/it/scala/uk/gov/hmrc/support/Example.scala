package uk.gov.hmrc.support

import uk.gov.hmrc.govukfrontend.examples.ExampleType

case class Example(componentName: String, frontend: ExampleType, exampleName: String, html: String)
