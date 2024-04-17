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

object TwirlFormatter {

  import ConfigFromReference._

  def format(parsed: NunjucksTemplate): String = {
    val importStatement: String =
      if (parsed.imports.isEmpty) Import().toString else parsed.imports.map(_.toString).toSet.mkString("\n")

    (importStatement :: parameterList(parsed.imports, requiresMessagesImport(parsed.body)) :: parsed.prelim.fold("")(
      _.toString
    ) :: injectingDependencies(parsed.body))
      .map(_.toString)
      .mkString("\n")
  }

  private def parameterList(imports: List[Import], addMessagesImport: Boolean) = {
    val dependencyInjections = imports.map(_.toDependencyInjectionString).mkString(",\n")
    val parameters           = if (addMessagesImport) "@()(implicit messages: Messages, request: RequestHeader)" else "@()"

    s"""
       |@this($dependencyInjections)
       |
       |$parameters
       |""".stripMargin
  }

  private def injectingDependencies(body: List[NunjucksTemplateBody]): List[String] = body.collect {
    case m: MacroCall => m.toDependencyInjectionString
    case m: CallMacro => m.toDependencyInjectionString
    case m: SetBlock  => m.toDependencyInjectionString
    case o            => o.toString
  }

  private def requiresMessagesImport(body: List[NunjucksTemplateBody]): Boolean =
    body.exists(template => componentsRequiringMessages.exists(template.toString.contains))
}
