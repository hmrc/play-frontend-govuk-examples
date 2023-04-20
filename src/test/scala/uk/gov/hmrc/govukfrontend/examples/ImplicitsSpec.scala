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

package uk.gov.hmrc.govukfrontend.examples

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.util.{Failure, Success, Try}

class ImplicitsSpec extends AnyWordSpec with Matchers {

  trait Abstract

  case class TestCaseClass() extends Abstract
  case object TestCaseObject extends Abstract

  class NonCaseClassProduct extends Product {
    override def productElement(n: Int): Any  = throw new Exception
    override def productArity: Int            = throw new Exception
    override def canEqual(that: Any): Boolean = throw new Exception
  }

  val testCaseClass: TestCaseClass             = TestCaseClass()
  val nonCaseClassProduct: NonCaseClassProduct = new NonCaseClassProduct

  "withCaseClassOps" should {

    "wrap case class with case class ops when imported" in {
      import uk.gov.hmrc.govukfrontend.examples.Implicits.withCaseClassOps

      Try {
        val foo: CaseClassOps[_] = testCaseClass
      } match {
        case Success(_) => succeed
        case _          => fail()
      }
    }

    "raise an Exception if attempting to import with case object in scope" in {
      import uk.gov.hmrc.govukfrontend.examples.Implicits.withCaseClassOps

      Try {
        val foo: CaseClassOps[_] = TestCaseObject
      } match {
        case Failure(_) => succeed
        case _          => fail()
      }
    }

    "raise an Exception if attempting to import with other invalid Product in scope" in {
      import uk.gov.hmrc.govukfrontend.examples.Implicits.withCaseClassOps
      Try {
        val foo: CaseClassOps[_] = nonCaseClassProduct
      } match {
        case Failure(_) => succeed
        case _          => fail()
      }
    }

  }

  "ProductOps" should {

    import uk.gov.hmrc.govukfrontend.examples.Implicits.ProductOps

    "isCaseType" should {

      "return true for case classes" in {
        testCaseClass.isCaseType shouldBe true
      }

      "return true for case objects" in {
        TestCaseObject.isCaseType shouldBe true
      }

      "return false otherwise" in {
        nonCaseClassProduct.isCaseType shouldBe false
      }

    }

    "isCaseClass" should {

      "return true for case classes" in {
        testCaseClass.isCaseClass shouldBe true
      }

      "return false for case objects" in {
        TestCaseObject.isCaseClass shouldBe false
      }

      "return false otherwise" in {
        nonCaseClassProduct.isCaseClass shouldBe false
      }

    }

    "isCaseObject" should {

      "return false for case classes" in {
        testCaseClass.isCaseObject shouldBe false
      }

      "return true for case objects" in {
        TestCaseObject.isCaseObject shouldBe true
      }

      "return false otherwise" in {
        nonCaseClassProduct.isCaseObject shouldBe false
      }

    }

    "asCaseClassOps" should {

      "return Some(CaseClassOps) for case classes" in {
        testCaseClass.asCaseClassOps match {
          case Some(_: CaseClassOps[_]) => succeed
          case _                        => fail()
        }
      }

      "return None for case objects" in {
        TestCaseObject.asCaseClassOps match {
          case None => succeed
          case _    => fail()
        }
      }

      "return None otherwise" in {
        nonCaseClassProduct.asCaseClassOps match {
          case None => succeed
          case _    => fail()
        }
      }

    }

  }

}
