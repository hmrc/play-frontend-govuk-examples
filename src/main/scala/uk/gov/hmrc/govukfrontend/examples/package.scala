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

import java.lang.reflect.Method

import play.twirl.api.Html
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.HtmlContent
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.Fieldset

package object examples {

  def prettyPrint(a: Any, indentSize: Int = 2, maxElementWidth: Int = 30, depth: Int = 1): String = {
    val indent      = " " * depth * indentSize
    val fieldIndent = indent + (" " * indentSize)
    val thisDepth   = prettyPrint(_: Any, indentSize, maxElementWidth, depth)
    val nextDepth   = prettyPrint(_: Any, indentSize, maxElementWidth, depth + 1)
    a match {
      case (k: String, v: String) => s""""$k" -> "$v""""
      case Some(value: String)    => s"""Some("$value")"""
      case HtmlContent(value: Html) =>
        if (value.body matches "\\w+") s"""HtmlContent($value)"""
        else s"""HtmlContent(\"\"\"$value\"\"\")"""
      // Make Strings look similar to their literal form.
      case s: String =>
        val replaceMap = Seq(
          "\n" -> "\\n",
          "\r" -> "\\r",
          "\t" -> "\\t",
          "\"" -> "\\\""
        )
        "\"" + replaceMap.foldLeft(s.trim) { case (acc, (c, r)) => acc.replace(c, r) } + "\""
      // For an empty Seq just use its normal String representation.
      case xs: Seq[_] if xs.isEmpty => xs.toString()
      case xs: Seq[_]               =>
        // If the Seq is not too long, pretty print on one line.
        val resultOneLine = xs.map(nextDepth).toString()
        if (resultOneLine.length <= maxElementWidth)
          return resultOneLine
        // Otherwise, build it with newlines and proper field indents.
        val result = xs.map(x => s"\n$fieldIndent${nextDepth(x)}").toString()
        result.substring(0, result.length - 1) + "\n" + indent + ")"
      // For an empty Map just use its normal String representation.
      case xs: Map[_, _] if xs.isEmpty => ""
      case xs: Map[_, _]               =>
        // If the Seq is not too long, pretty print on one line.
        val resultOneLine = s"Map(${xs.map(nextDepth)})"
        if (resultOneLine.length <= maxElementWidth)
          return resultOneLine
        // Otherwise, build it with newlines and proper field indents.
        val result = s"""Map(${xs.map(x => s"\n$fieldIndent${nextDepth(x)}").toString().replaceFirst("List\\(", "")}"""
        result.substring(0, result.length - 1) + "\n" + indent + ")"
      // Product should cover case classes.
      case p: Product =>
        val prefix = p.productPrefix
        // We'll use reflection to get the constructor arg names and values.
        val cls    = p.getClass
        val fields = cls.getDeclaredFields.filterNot(_.isSynthetic).map(_.getName).filterNot(_ == "serialVersionUID")
        val values = p.productIterator.toSeq
        // If we weren't able to match up fields/values, fall back to toString.
        if (fields.length != values.length)
          return p.toString
        fields.zip(values).toList match {
          // If there are no fields, just use the normal String representation.
          case Nil => p.toString
          // If there is just one field, let's just print it as a wrapper.
          case (_, value) :: Nil =>
            s"$prefix(${thisDepth(value)})"
          // If there is more than one field, build up the field names and values.
          case kvps =>
            val defaults: Array[(String, Any)] = getDefaults(p)
            val prettyFields = kvps
              .filterNot(kvp => defaults.contains(kvp))
              .map {
                case (k, v) =>
                  val value = nextDepth(v)
                  if (!(value matches """[\s]+|^$|Empty|\"\"|Map\(\)|None|false|1"""))
                    s"$fieldIndent$k = $value"
                  else ""
              }
              .filterNot(_ matches """^[\s]+$|^$|Empty|\"\"|Map\(\)|None|false|1""")
            // If the result is not too long, pretty print on one line.
            if (prettyFields.nonEmpty) {
              val resultOneLine = s"$prefix(${prettyFields.mkString(", ")})"
              if (resultOneLine.length <= maxElementWidth) return resultOneLine
              // Otherwise, build it with newlines and proper field indents.
              s"$prefix(\n${prettyFields.mkString(",\n")}\n$indent)"
            } else {
              ""
            }
        }
      // If we haven't specialized this type, just use its toString.
      case _ => a.toString
    }
  }

  private def getCompanionObj[T <: Product](caseClassObj: T): Any = {
    val caseClass: Class[_ <: T] = caseClassObj.getClass
    val classLoader              = caseClass.getClassLoader
    val companionObj             = classLoader.loadClass(s"${caseClass.getName}$$")
    companionObj.getDeclaredField("MODULE$").get(true)
  }

  private def getDefaults[T <: Product](caseClassObj: T): Array[(String, Any)] = {
    val compObj                    = getCompanionObj(caseClassObj)
    val compMethods: Array[Method] = compObj.getClass.getDeclaredMethods
    val fields                     = caseClassObj.getClass.getDeclaredFields.map(_.getName)

    for {
      method: Method <- compMethods
      javaMethodName: String = method.getName
      defaultParamIndex <- """\$lessinit\$greater\$default\$(\d)""".r
                            .findFirstMatchIn(javaMethodName)
                            .toIterable
                            .flatMap(_.subgroups)
                            .map(_.toInt - 1)
      field: String = fields(defaultParamIndex)
      default: Any  = method.invoke(compObj)
    } yield field -> default
  }

  sealed trait NunjucksTemplateBody

  case class NunjucksTemplate(
    prelim: Option[TemplateHtml] = None,
    imports: List[Import],
    body: List[NunjucksTemplateBody])

  case class Import(from: String = "", macroName: String = "") {

    override def toString: String = "@import uk.gov.hmrc.govukfrontend.views.html.components._"

    def toDependencyInjectionString: String = s"$macroName : ${macroName.capitalize}"
  }

  case class MacroCall(macroName: String, args: Any) extends NunjucksTemplateBody {

    override def toString: String = s"""@${macroName.capitalize}(${prettyPrint(args)})""".replaceAll("\\sList", " Seq")

    def toDependencyInjectionString: String = s"""@$macroName(${prettyPrint(args)})""".replaceAll("\\sList", " Seq")
  }

  case class SetBlock(blockName: String, html: Option[TemplateHtml] = None, macroCall: MacroCall)
      extends NunjucksTemplateBody {

    private val htmlContent = html.fold("")(_.toString + "\n")

    override def toString: String =
      s"""@$blockName = {\n$htmlContent${macroCall.toString}\n}""".replaceAll("\\sList", " Seq")

    def toDependencyInjectionString: String =
      s"""@$blockName = {\n$htmlContent${macroCall.toDependencyInjectionString}\n}""".replaceAll("\\sList", " Seq")
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
            .replaceAll("\\sList", " Seq")
        case _ => ""
      }
  }

  case class TemplateHtml(content: Html) extends NunjucksTemplateBody {

    override def toString: String = content.toString()
  }

  case class WhiteSpace(ws: String) extends NunjucksTemplateBody
}
