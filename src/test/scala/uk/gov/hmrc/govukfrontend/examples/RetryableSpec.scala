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

import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{ParallelTestExecution}

import scala.concurrent.Future

class RetryableSpec extends AsyncWordSpec with Matchers with ParallelTestExecution with Retryable {

  "retryUntil" should {

    "do nothing if condition already true" in {
      val condition: Boolean = true
      Future {
        retryUntil(condition)(fail("Method to retry should not have been called if condition was already true."))
      }.map(_ => succeed)
    }

    "throw exception after max number of attempts exceeded" in {
      val expectedAttempts: Int = 5
      var actualAttempts: Int   = 0
      val condition: Boolean    = false
      Future(retryUntil(condition, None, expectedAttempts)((actualAttempts = actualAttempts + 1)))
        .map(_ =>
          fail(
            "An exception should have been thrown to indicate max number of attempts was exceeded before condition was achieved."
          )
        )
        .recover { case _: Exception => assert(actualAttempts == expectedAttempts) }
    }

    "succeed if reach max number of attempts and condition is true, then stop" in {
      val maxAttempts: Int      = 5
      val expectedAttempts: Int = maxAttempts
      var actualAttempts: Int   = 0
      def condition: Boolean    = expectedAttempts == actualAttempts
      Future(retryUntil(condition, None, maxAttempts)((actualAttempts = actualAttempts + 1)))
        .map(_ => assert(actualAttempts == expectedAttempts))
    }

    "succeed if reach less than max number of attempts and condition is true, then stop" in {
      val maxAttempts: Int      = 5
      val expectedAttempts: Int = 2
      var actualAttempts: Int   = 0
      def condition: Boolean    = expectedAttempts == actualAttempts
      Future(retryUntil(condition, None, maxAttempts)((actualAttempts = actualAttempts + 1)))
        .map(_ => assert(actualAttempts == expectedAttempts))
    }

  }

}
