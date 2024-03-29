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

package uk.gov.hmrc.govukfrontend

import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset
import uk.gov.hmrc.govukfrontend.examples.Implicits.ProductOps

package object examples {
  def standardiseSeq(in: String) =
    in.replaceAll("""([\s(])(List|Vector)""", "$1Seq")

  def prettyPrint(a: Any, indentSize: Int = 2, maxElementWidth: Int = 30, depth: Int = 1): String = {
    val indent      = " " * depth * indentSize
    val fieldIndent = indent + (" " * indentSize)
    val thisDepth   = prettyPrint(_: Any, indentSize, maxElementWidth, depth)
    val nextDepth   = prettyPrint(_: Any, indentSize, maxElementWidth, depth + 1)
    a match {
      case Nil                         => ""
      case (k: String, v: String)      => s""""$k" -> "$v""""
      case Some(value: String)         => s"""Some("$value")"""
      case HtmlContent(value: Html)    =>
        if (value.body matches "\\w+") s"""HtmlContent($value)"""
        else s"""HtmlContent(\"\"\"$value\"\"\")"""
      // Make Strings look similar to their literal form.
      case s: String                   =>
        val replaceMap = Seq(
          "\n" -> "\\n",
          "\r" -> "\\r",
          "\t" -> "\\t",
          "\"" -> "\\\""
        )
        "\"" + replaceMap.foldLeft(s.trim) { case (acc, (c, r)) => acc.replace(c, r) } + "\""
      // For an empty Seq just use its normal String representation.
      case xs: Seq[_] if xs.isEmpty    => xs.toString()
      case xs: Seq[_]                  =>
        // If the Seq is not too long, pretty print on one line.
        val resultOneLine = xs.map(nextDepth).toString()
        if (resultOneLine.length <= maxElementWidth)
          resultOneLine
        // Otherwise, build it with newlines and proper field indents.
        else {
          val result = xs.map(x => s"\n$fieldIndent${nextDepth(x)}").toString()
          result.substring(0, result.length - 1) + "\n" + indent + ")"
        }
      // For an empty Map just use its normal String representation.
      case xs: Map[_, _] if xs.isEmpty => ""
      case xs: Map[_, _]               =>
        // If the Seq is not too long, pretty print on one line.
        val resultOneLine = s"Map(${xs.map(nextDepth)})"
        if (resultOneLine.length <= maxElementWidth)
          resultOneLine
        // Otherwise, build it with newlines and proper field indents.
        else {
          val result =
            s"""Map(${xs.map(x => s"\n$fieldIndent${nextDepth(x)}").toString().replaceFirst("List\\(", "")}"""
          result.substring(0, result.length - 1) + "\n" + indent + ")"
        }
      case p: Product if p.isCaseClass =>
        val prefix                                        = p.productPrefix
        import uk.gov.hmrc.govukfrontend.examples.Implicits.withCaseClassOps
        val customisedConstructorFields: List[Field[Any]] = p.customisedConstructorFields

        customisedConstructorFields match {
          // This is currently a quick-fix for MacroCalls such that when their model case class has values that are
          // identical to the default arguments (i.e. no customised fields), no argument needs be passed, as the
          // case class model with only default arguments is the default argument of the MacroCall.
          case Nil                                                             => ""
          // If there is just one field in the case class constructor, let's just print it as a wrapper.
          case Field(_, value, _, _) :: Nil if p.constructorFields.length == 1 =>
            s"$prefix(${thisDepth(value)})"
          // If there is more than one field, build up the field names and values.
          case _                                                               =>
            val prettyFields  = customisedConstructorFields
              .map { f =>
                s"$fieldIndent${f.name} = ${nextDepth(f.value)}"
              }
            // If the result is not too long, pretty print on one line.
            val resultOneLine = s"$prefix(${prettyFields.mkString(", ")})"
            if (resultOneLine.length <= maxElementWidth)
              resultOneLine
            // Otherwise, build it with newlines and proper field indents.
            else
              s"$prefix(\n${prettyFields.mkString(",\n")}\n$indent)"
        }
      // If we haven't specialized this type, just use its toString.
      case _                           => a.toString
    }
  }

  sealed trait NunjucksTemplateBody

  case class NunjucksTemplate(
    prelim: Option[TemplateHtml] = None,
    imports: List[Import],
    body: List[NunjucksTemplateBody]
  )

  case class Import(from: String = "", macroName: String = "") {

    override def toString: String =
      if (macroName.startsWith("hmrc")) "@import uk.gov.hmrc.hmrcfrontend.views.html.components._"
      else "@import uk.gov.hmrc.govukfrontend.views.html.components._"

    def toDependencyInjectionString: String = s"$macroName : ${macroName.capitalize}"
  }

  case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody {

    override def toString: String = standardiseSeq(s"""@${macroName.capitalize}(${prettyPrint(args)})""")

    def toDependencyInjectionString: String = standardiseSeq(s"""@$macroName(${prettyPrint(args)})""")
  }

  case class SetBlock(blockName: String, html: Option[TemplateHtml] = None, macroCall: Option[MacroCall])
      extends NunjucksTemplateBody {

    private val htmlContent = html.fold("")(_.toString + "\n")

    override def toString: String =
      s"""@$blockName = {\n$htmlContent${macroCall.getOrElse("").toString}\n}"""

    def toDependencyInjectionString: String =
      s"""@$blockName = {\n$htmlContent${macroCall.map(_.toDependencyInjectionString).getOrElse("")}\n}"""
  }

  case class CallMacro(callMacro: MacroCall, macroCalls: List[MacroCall]) extends NunjucksTemplateBody {

    override def toString: String = {
      val toStringFunction: MacroCall => String = _.toString
      stringify(toStringFunction, callMacro.macroName.capitalize)
    }

    def toDependencyInjectionString: String = {
      val toStringFunction: MacroCall => String = _.toDependencyInjectionString
      stringify(toStringFunction, callMacro.macroName)
    }

    private def stringify(toStringFunction: MacroCall => String, callMacroName: String) =
      callMacro.args match {
        case fieldset: Fieldset =>
          val fieldsetWithHtml = fieldset.copy(html = Html("html"))
          val macroCallsHtml   = macroCalls.map(toStringFunction).mkString("\n")
          s"""@$callMacroName(${prettyPrint(fieldsetWithHtml)})
             |
             |@html = {
             |$macroCallsHtml
             |}
             |""".stripMargin
        case _                  => ""
      }
  }

  case class TemplateHtml(content: Html) extends NunjucksTemplateBody {

    override def toString: String = content.toString()
  }

  case class WhiteSpace(ws: String) extends NunjucksTemplateBody

}
