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

package uk.gov.hmrc.hmrcfrontend.views.example

import play.api.i18n.Messages
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import uk.gov.hmrc.govukfrontend.examples.{HmrcFrontend, MessagesSupport}
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcFeedbackIntegrationSpec extends TemplateIntegrationSpec {

  val request: RequestHeader = FakeRequest()
  val messages: Messages     = MessagesSupport().messages

  testRendering(HmrcFrontend, "feedback", "link", feedbackLink.f)
  testRendering(HmrcFrontend, "feedback", "linkWelsh", feedbackLinkWelsh.f)
  testRendering(HmrcFrontend, "feedback", "survey", () => feedbackSurvey.render(messages, request))
  testRendering(HmrcFrontend, "feedback", "thanks", feedbackThanks.f)
  testRendering(HmrcFrontend, "feedback", "thanksWelsh", feedbackThanksWelsh.f)

  // Excluded as the ordering of the attributes is different in Nunjucks and Twirl
  // testRendering(HmrcFrontend, "feedback", "surveyWelsh", () => feedbackSurveyWelsh.render(messages, request))

}
