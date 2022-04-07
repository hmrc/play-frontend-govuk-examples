/*
 * Copyright 2022 HM Revenue & Customs
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

class CaseClassOpsSpec extends AnyWordSpec with Matchers {

  sealed trait Abstract {
    val zero: String
    val six: Float
    val thirteen: Option[String]
    protected val fifteen: Option[Float]
  }

  case class TestCaseClass(
    zero: String,
    one: Boolean = true,
    two: Int = 2,
    three: Option[Int] = None,
    four: List[Int] = List(4),
    five: Double,
    six: Float,
    seven: BigDecimal,
    eight: String = "eight",
    nine: Int,
    ten: Option[Int] = Some(10),
    private val eleven: String,
    protected val twelve: Int
  ) extends Abstract {
    val thirteen: Option[String]         = Some("thirteen")
    private val fourteen: Option[Double] = Some(14)
    protected val fifteen: Option[Float] = Some(15)
  }

  val testOne: TestCaseClass = TestCaseClass(
    zero = "zero",
    five = 5,
    six = 6,
    seven = 7,
    nine = 9,
    eleven = "eleven",
    twelve = 12
  )

  val testOneOps: CaseClassOps[TestCaseClass] = CaseClassOps(testOne)

  val testTwo: TestCaseClass = TestCaseClass(
    zero = "zero",
    one = false,
    two = 22222,
    three = Some(3),
    five = 5,
    six = 6,
    seven = 7,
    nine = 9,
    ten = None,
    eleven = "eleven",
    twelve = 12
  )

  val testTwoOps: CaseClassOps[TestCaseClass] = CaseClassOps(testTwo)

  "fields" should {

    """provide a list of all defined fields in a case class,
       |their names, their values, their default values and
       |whether they are used in the case class constructor,
       |all in the order they are defined""".stripMargin in {

      val expectedTestOneFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = true,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 2,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = None,
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[List[Int]](
          name = "four",
          value = List(4),
          defaultValue = Some(List(4)),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eight",
          value = "eight",
          defaultValue = Some("eight"),
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = Some(10),
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[String]](
          name = "thirteen",
          value = Some("thirteen"),
          defaultValue = Some(Some("thirteen")),
          isConstructorVal = false
        ),
        Field[Option[Double]](
          name = "fourteen",
          value = Some(14),
          defaultValue = Some(Some(14)),
          isConstructorVal = false
        ),
        Field[Option[Float]](
          name = "fifteen",
          value = Some(15),
          defaultValue = Some(Some(15)),
          isConstructorVal = false
        )
      )

      val testOneFields: List[Field[Any]] = testOneOps.fields

      val expectedTestTwoFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = false,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 22222,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = Some(3),
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[List[Int]](
          name = "four",
          value = List(4),
          defaultValue = Some(List(4)),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eight",
          value = "eight",
          defaultValue = Some("eight"),
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = None,
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[String]](
          name = "thirteen",
          value = Some("thirteen"),
          defaultValue = Some(Some("thirteen")),
          isConstructorVal = false
        ),
        Field[Option[Double]](
          name = "fourteen",
          value = Some(14),
          defaultValue = Some(Some(14)),
          isConstructorVal = false
        ),
        Field[Option[Float]](
          name = "fifteen",
          value = Some(15),
          defaultValue = Some(Some(15)),
          isConstructorVal = false
        )
      )

      val testTwoFields: List[Field[Any]] = testTwoOps.fields

      testOneFields shouldBe expectedTestOneFields

      testTwoFields shouldBe expectedTestTwoFields

    }
  }

  "constructorFields" should {

    """provide a list of all constructor fields in a case class in the order they are defined""" in {

      val expectedTestOneConstructorFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = true,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 2,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = None,
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[List[Int]](
          name = "four",
          value = List(4),
          defaultValue = Some(List(4)),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eight",
          value = "eight",
          defaultValue = Some("eight"),
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = Some(10),
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testOneConstructorFields: List[Field[Any]] = testOneOps.constructorFields

      val expectedTestTwoConstructorFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = false,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 22222,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = Some(3),
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[List[Int]](
          name = "four",
          value = List(4),
          defaultValue = Some(List(4)),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eight",
          value = "eight",
          defaultValue = Some("eight"),
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = None,
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testTwoConstructorFields: List[Field[Any]] = testTwoOps.constructorFields

      testOneConstructorFields shouldBe expectedTestOneConstructorFields

      testTwoConstructorFields shouldBe expectedTestTwoConstructorFields

    }

  }

  "customisedFields" should {

    """provide a list of all fields in a case class
      |in the order they are defined if they are
      |different from any set default value for that field""".stripMargin in {

      val expectedTestOneCustomisedFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testOneCustomisedFields: List[Field[Any]] = testOneOps.customisedFields

      val expectedTestTwoCustomisedFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = false,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 22222,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = Some(3),
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = None,
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testTwoCustomisedFields: List[Field[Any]] = testTwoOps.customisedFields

      testOneCustomisedFields shouldBe expectedTestOneCustomisedFields

      testTwoCustomisedFields shouldBe expectedTestTwoCustomisedFields

    }

  }

  "customisedConstructorFields" should {

    """provide a list of all constructor fields in a case
      |class in the order they are defined if they are
      |different from any set default value for that field""".stripMargin in {

      val expectedTestOneCustomisedConstructorFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testOneCustomisedConstructorFields: List[Field[Any]] = testOneOps.customisedConstructorFields

      val expectedTestTwoCustomisedConstructorFields: List[Field[Any]] = List(
        Field[String](
          name = "zero",
          value = "zero",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Boolean](
          name = "one",
          value = false,
          defaultValue = Some(true),
          isConstructorVal = true
        ),
        Field[Int](
          name = "two",
          value = 22222,
          defaultValue = Some(2),
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "three",
          value = Some(3),
          defaultValue = Some(None),
          isConstructorVal = true
        ),
        Field[Double](
          name = "five",
          value = 5,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Float](
          name = "six",
          value = 6,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[BigDecimal](
          name = "seven",
          value = 7,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "nine",
          value = 9,
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Option[Int]](
          name = "ten",
          value = None,
          defaultValue = Some(Some(10)),
          isConstructorVal = true
        ),
        Field[String](
          name = "eleven",
          value = "eleven",
          defaultValue = None,
          isConstructorVal = true
        ),
        Field[Int](
          name = "twelve",
          value = 12,
          defaultValue = None,
          isConstructorVal = true
        )
      )

      val testTwoCustomisedConstructorFields: List[Field[Any]] = testTwoOps.customisedConstructorFields

      testOneCustomisedConstructorFields shouldBe expectedTestOneCustomisedConstructorFields

      testTwoCustomisedConstructorFields shouldBe expectedTestTwoCustomisedConstructorFields

    }

  }

}
