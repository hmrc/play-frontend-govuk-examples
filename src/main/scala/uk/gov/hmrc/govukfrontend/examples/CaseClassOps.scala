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

import java.lang.reflect.{Method, Field => JField}

import scala.reflect.ClassTag
import scala.reflect.runtime.currentMirror
import scala.util.matching.Regex

case class Field[+T](
  name: String,
  value: T,
  defaultValue: Option[T],
  isConstructorVal: Boolean
) {}

case class CaseClassOps[T <: Product: ClassTag](caseClassObj: T) {

  private val caseClass       = caseClassObj.getClass
  private val caseClassMirror = currentMirror.reflect(caseClassObj)

  private val companionObjInstance: Any = {
    val classLoader  = caseClass.getClassLoader
    val companionObj = classLoader.loadClass(s"${caseClass.getName}$$")

    val moduleMaybe = companionObj.getDeclaredFields.find(_.getName == "MODULE$")
    moduleMaybe match {
      case Some(module) => module.get(true)
      case _            =>
        val outerModule  = caseClass.getFields.find(_.getName.contains("$")).get.get(caseClassObj)
        val moduleMirror = currentMirror.reflect(outerModule)

        val companionSymbol = caseClassMirror.symbol.companion.asModule
        moduleMirror.reflectModule(companionSymbol).instance
    }
  }

  val javaFields: Array[JField] = {
    val fields = caseClass.getDeclaredFields
      .filterNot(f => f.isSynthetic | f.getName.contains("serialVersionUID"))
    fields.foreach(_.setAccessible(true))
    fields
  }

  private val jFieldTrueNames: Map[JField, String] = {
    val nonPublicField: Regex = """.*?\$\$(\w*)$""".r

    val kvps = for {
      field    <- javaFields
      fieldName = field.getName match {
                    case nonPublicField(trueName) => trueName
                    case f @ _                    => f
                  }
    } yield field -> fieldName

    kvps.toMap
  }

  private val caseAccessors: Iterable[String] = {
    val nonPublicCaseAccessor: Regex = """^([^$]*).*$""".r

    val members = caseClassMirror.symbol.typeSignature.members

    for {
      member      <- members
      if member.isMethod
      methodSymbol = member.asMethod
      if methodSymbol.isCaseAccessor
      fieldName    = methodSymbol.name.toString
    } yield fieldName match {
      case nonPublicCaseAccessor(trueName) => trueName
      case _                               => fieldName
    }
  }

  private val defaults: Map[String, Any] = {
    val fieldNames: Seq[String] = javaFields.map(jFieldTrueNames)
    val kvps                    = for {
      compObjMethod: Method <- companionObjInstance.getClass.getDeclaredMethods
      javaMethodName: String = compObjMethod.getName
      defaultParamIndex     <-
        """\$lessinit\$greater\$default\$(\d+)""".r
          .findFirstMatchIn(javaMethodName)
          .toIterable
          .flatMap(_.subgroups)
          .map(_.toInt - 1)
      field: String          = fieldNames(defaultParamIndex)
      default: Any           = compObjMethod.invoke(companionObjInstance)
    } yield field -> default

    kvps.toMap
  }

  val fields: List[Field[Any]] = {
    val array = for {
      jField          <- javaFields
      fieldName        = jFieldTrueNames(jField)
      value            = jField.get(caseClassObj)
      isConstructorVal = caseAccessors.toSeq.contains(fieldName)
      defaultValue     = if (isConstructorVal) defaults.get(fieldName) else Some(value)
    } yield Field(
      name = fieldName,
      value = value,
      defaultValue = defaultValue,
      isConstructorVal = isConstructorVal
    )

    array.toList
  }

  val constructorFields: List[Field[Any]]           = fields.filter(_.isConstructorVal)
  val customisedFields: List[Field[Any]]            = fields.filterNot(f => f.defaultValue.contains(f.value))
  val customisedConstructorFields: List[Field[Any]] = customisedFields.filter(_.isConstructorVal)

}
