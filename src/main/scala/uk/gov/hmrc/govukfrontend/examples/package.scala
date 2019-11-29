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

package uk.gov.hmrc.govukfrontend

import play.twirl.api.Html

package object examples {

  case class NunjucksTemplate(imports: List[Import], body: List[NunjucksTemplateBody])
  case class Import(from: String, macroName: String) {
    override def toString: String = "@import uk.gov.hmrc.govukfrontend.views.html.components._"
    def toDependencyInjectionString: String = s"$macroName : ${macroName.capitalize}"
  }

  sealed trait NunjucksTemplateBody

  case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody {
    override def toString: String = s"""@${macroName.capitalize}(${args.valueTreeString()})"""
  }
  case class TemplateHtml(content: Html) extends NunjucksTemplateBody {
    override def toString: String = content.toString()
  }
  case class WhiteSpace(ws: String) extends NunjucksTemplateBody

  // Adapted the following from: https://github.com/nikita-volkov/sext/blob/master/src/main/scala/sext/package.scala

  import reflect.runtime.currentMirror
  import reflect.runtime.universe._

  implicit class SextAnyTreeString[A](a: A) {

    /**
      * @return A readable string representation of this value of a different format to `treeString`
      */
    def valueTreeString(showKey: Boolean = true): String = a match {

      case (k, v) =>
        val valueWithKey = v.valueTreeString()
        if (!("""[\s]+|^$""" matches valueWithKey) && showKey) {
          if (s"$valueWithKey" matches """.*[\r\n]+.*""")
            s"$k = \n$valueWithKey"
          else
            s"$k = $valueWithKey"
        } else {
          val valueWithoutKey = v.valueTreeString(showKey)
          if (!("""[\s]+|^$""" matches valueWithoutKey))
            s"\n$valueWithoutKey"
          else ""
        }

      case Some(value) =>
        s"Some(${value.valueTreeString(showKey = false)})"

      case a: TraversableOnce[_] =>
        val b = a.toStream
          .map(_.valueTreeString())
          .filterNot(_ matches """[\s]+|^$|[\s]*.*=[\r\n\s]+$""")
          .mkString(s",\n")

        if (Seq("scala.collection.immutable.$colon$colon").contains(currentMirror.reflect(a).symbol.fullName))
          s"\nSeq($b)"
        else b

      case a: Product =>
        val b = currentMirror
          .reflect(a)
          .symbol
          .typeSignature
          .members
          .toStream
          .collect { case a: TermSymbol => a }
          .filterNot(_.isMethod)
          .filterNot(_.isModule)
          .filterNot(_.isClass)
          .map(currentMirror.reflect(a).reflectField)
          .map(f => f.symbol.name.toString.trim -> f.get)
          .reverse

        if (Seq("scala.None", "uk.gov.hmrc.govukfrontend.views.viewmodels.content.Empty")
              .contains(currentMirror.reflect(a).symbol.fullName)) ""
        else if (Seq("scala.Some", "uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text")
                   .contains(currentMirror.reflect(a).symbol.fullName))
          s"""${currentMirror.reflect(a).symbol.name}(${collection.immutable.ListMap(b: _*).valueTreeString()})"""
        else
          s"""\n${currentMirror.reflect(a).symbol.name}(${collection.immutable.ListMap(b: _*).valueTreeString()})"""

      case null => ""

      case boolean: Boolean => if (boolean) s"$boolean" else ""

      case string: String => if (string == "") "" else s""""$string""""

      case _ => a.toString
    }

  }

}
