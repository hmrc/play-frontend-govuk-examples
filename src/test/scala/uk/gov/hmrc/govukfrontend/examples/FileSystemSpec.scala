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

import org.scalatest.{AsyncWordSpec, Matchers, ParallelTestExecution, Succeeded}
import uk.gov.hmrc.govukfrontend.examples.FileSystem.{TrueFile, prepareDirStructure}

import scala.concurrent.Future
import scala.reflect.io.{Directory, File}

class FileSystemSpec extends AsyncWordSpec with Matchers with ParallelTestExecution {

  private val currentDir: String = System.getProperty("user.dir")
  private val targetDir          = s"$currentDir/target"
  private val testNameSpace      = s"$targetDir/FileSystemSpec"
  private val testDir            = Directory(testNameSpace)

  "ensure" should {}

  "prepareDirStructure" should {

    "successfully create files and ancestor dirs when ancestor dirs are shared" in {
      val testPrecond: Future[AnyVal] = Future(if (testDir.exists) testDir.deleteRecursively())

      val sharedAncestors      = s"$testNameSpace/a/b/c/d/e/f/g/h"
      val sharedAncestorsTwo   = s"$sharedAncestors/i/j/k/l"
      val sharedAncestorsThree = s"$sharedAncestorsTwo/m/n/o/p"

      val fileOnePath   = s"$sharedAncestors/fileOne.scala.html"
      val fileTwoPath   = s"$sharedAncestors/fileTwo.scala.html"
      val fileThreePath = s"$sharedAncestorsTwo/fileThree.scala.html"
      val fileFourPath  = s"$sharedAncestorsThree/fileFour.scala.html"
      val fileFivePath  = s"$testNameSpace/fileFive.scala.html"
      val filePaths     = Iterable(fileOnePath, fileTwoPath, fileThreePath, fileFourPath, fileFivePath)

      val fileOne   = TrueFile(Paths.get(fileOnePath))
      val fileTwo   = TrueFile(Paths.get(fileTwoPath))
      val fileThree = TrueFile(Paths.get(fileThreePath))
      val fileFour  = TrueFile(Paths.get(fileFourPath))
      val fileFive  = TrueFile(Paths.get(fileFivePath))

      val files = Iterable(fileOne, fileTwo, fileThree, fileFour, fileFive)

      val dirCreation: Future[Unit] = prepareDirStructure(files)

      for {
        _          <- testPrecond
        _          <- dirCreation
        assertions <- Future.traverse(filePaths)(path =>
                        assert(File(path).exists, s"Expected path to file (including file) [$path] to be created.")
                      )
      } yield assert(assertions.forall(_ == Succeeded))
    }

  }

}
