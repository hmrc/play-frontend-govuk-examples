/*
 * Copyright 2019 HM Revenue & Customs
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

object PlayVersions {

  sealed trait PlayVersion {
    val version: Int
    override def toString: String = version.toString
  }

  case class Play25() extends PlayVersion {
    val version: Int              = 25
    override def toString: String = "play-25"
  }
  case class Play26() extends PlayVersion {
    val version: Int              = 26
    override def toString: String = "play-26"
  }

  val implementedPlayVersions: Iterable[PlayVersion] = Iterable(Play25(), Play26())
}
