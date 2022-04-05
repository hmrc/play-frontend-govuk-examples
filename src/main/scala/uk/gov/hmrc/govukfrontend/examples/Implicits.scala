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

import scala.language.implicitConversions
import scala.reflect.ClassTag
import scala.reflect.runtime.currentMirror
import scala.util.Try

object Implicits {

  implicit def withCaseClassOps[T <: Product: ClassTag](caseClassObj: T): CaseClassOps[T] =
    if (!caseClassObj.isCaseClass)
      throw new Exception(
        s"Product [$caseClassObj] is not a case class - please limit the scope of implicit conversion or directly create CaseClassOps appropriately."
      )
    else
      CaseClassOps(caseClassObj)

  implicit class ProductOps[T <: Product: ClassTag](product: T) {

    val isCaseType: Boolean = currentMirror.reflect(product).symbol.isCaseClass

    private val hasCompanionObject: Boolean = {
      val productClass: Class[_ <: T] = product.getClass
      val classLoader                 = productClass.getClassLoader

      Try(classLoader.loadClass(s"${productClass.getName}$$")).isSuccess
    }

    val isCaseClass: Boolean = isCaseType && hasCompanionObject

    val isCaseObject: Boolean = isCaseType && !hasCompanionObject

    def asCaseClassOps: Option[CaseClassOps[T]] = if (isCaseClass) Some(CaseClassOps(product)) else None

  }

}
