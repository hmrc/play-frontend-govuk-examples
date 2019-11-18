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
  }

  sealed trait NunjucksTemplateBody

  case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody {
    override def toString: String = s"""@${macroName.capitalize}(${args.valueTreeString})"""
  }
  case class TemplateHtml(content: Html) extends NunjucksTemplateBody {
    override def toString: String = content.toString()
  }
  case class WhiteSpace(ws: String) extends NunjucksTemplateBody

  // Adapted the following from: https://github.com/nikita-volkov/sext/blob/master/src/main/scala/sext/package.scala

  import reflect.runtime.currentMirror
  import reflect.runtime.universe._

  implicit class SextAnyTreeString[A](a: A) {

    private def indent(s: String) = s.lines.toStream match {
      case h +: t =>
        (("" + h) +: t.map { "," + _ }) mkString " "
      case _ => ""
    }

    /**
      * @return A readable string representation of this value of a different format to `treeString`
      */
    def valueTreeString: String = a match {
      case (k, v) =>
        if ("" != v.valueTreeString)
          s"$k = " + v.valueTreeString
        else ""

      case a: TraversableOnce[_] =>
        a.toStream
          .map(_.valueTreeString)
          .map(indent)
          .filterNot(_ == "")
          .mkString(", ")

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
        if (currentMirror.reflect(a).symbol.fullName != "scala.None")
          s"""${currentMirror.reflect(a).symbol.name}(${collection.immutable.ListMap(b: _*).valueTreeString})"""
        else
          ""

      case null => ""

      case option: Option[_] => if (option.isDefined) s"Some(${option.get.valueTreeString})" else ""

      case boolean: Boolean => if (boolean) s"$boolean" else ""

      case string: String => if (string == "") "" else s""""$string""""

      case _ => a.toString
    }

  }

}
