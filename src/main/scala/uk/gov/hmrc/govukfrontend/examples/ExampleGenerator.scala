/*
 * Copyright 2021 HM Revenue & Customs
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

import java.nio.file.Paths

import uk.gov.hmrc.govukfrontend.examples.FileSystem.TrueDir

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class ExampleGenerator {

  val currentDir: String = System.getProperty("user.dir")
  val srcGovukDir        = s"$currentDir/govuk-design-system/src/components"
  val destGovukDir       = s"$currentDir/src/test"
  val srcHmrcDir         = s"$currentDir/hmrc-design-system/src/examples"
  val destHmrcDir        = s"$currentDir/src/test"

  private val govuk =
    ExampleTranslator
      .translateTwirlExamples(TrueDir(Paths.get(srcGovukDir)), TrueDir(Paths.get(destGovukDir)), GovukFrontend)
  Await.result(govuk, 30.second)

  private val hmrc =
    ExampleTranslator
      .translateTwirlExamples(TrueDir(Paths.get(srcHmrcDir)), TrueDir(Paths.get(destHmrcDir)), HmrcFrontend)
  Await.result(hmrc, 30.second)
}
