/*
 * Copyright 2024 HM Revenue & Customs
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

import java.nio.file.{Files, Path, Paths}
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.govukfrontend.examples.FileSystem.TrueDir

import scala.jdk.CollectionConverters._
import scala.reflect.io.Directory

class ExampleTranslatorTest extends AsyncWordSpec with Matchers {

  "ExampleTranslator" should {

    "generate Twirl examples" in {

      import ConfigFromReference._

      val currentDir: String = System.getProperty("user.dir")
      val srcDir             = s"$currentDir/govuk-design-system/src/components"
      val destDir            = s"$currentDir/target/destTwirlExamples"

      ExampleTranslator
        .translateTwirlExamples(
          TrueDir(Paths.get(srcDir)),
          TrueDir(Paths.get(destDir)),
          GovukFrontend
        )
        .flatMap { _ =>
          assert(Directory(destDir).exists)

          val noOfNunjucksFiles = countFiles(Paths.get(srcDir), "index.njk", excludedExamples)
          val noOfTwirlFiles    = countFiles(Paths.get(destDir, PlayVersions.Play2().toString), ".*.scala.html")

          noOfNunjucksFiles shouldBe noOfTwirlFiles
        }
    }
  }

  private def countFiles(path: Path, file: String, exclusions: List[String] = Nil) =
    Files
      .walk(path)
      .iterator()
      .asScala
      .filter(Files.isRegularFile(_))
      .count(path => path.getFileName.toString.matches(file) && !exclusions.exists(path.toString.contains))
}
