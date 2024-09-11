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

package uk.gov.hmrc.hmrcfrontend.views.example

import uk.gov.hmrc.govukfrontend.examples.{HmrcFrontend, MessagesSupport}
import uk.gov.hmrc.hmrcfrontend.views.html.examples._
import uk.gov.hmrc.support.TemplateIntegrationSpec

class hmrcFileUploadIntegrationSpec extends TemplateIntegrationSpec {

  testRendering(HmrcFrontend, "file-upload", "additionalRow", fileuploadAdditionalRow.f)
  testRendering(HmrcFrontend, "file-upload", "additionalRowWelsh", fileuploadAdditionalRowWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "errors", fileuploadErrors.f)
  testRendering(HmrcFrontend, "file-upload", "errorsWelsh", fileuploadErrorsWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "fixedWelsh", fileuploadFixedWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "question", fileuploadQuestion.f)
  testRendering(HmrcFrontend, "file-upload", "questionWelsh", fileuploadQuestionWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "types", fileuploadTypes.f)
  testRendering(HmrcFrontend, "file-upload", "typesWelsh", fileuploadTypesWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "uploadASingleFile", fileuploadUploadASingleFile.f)
  testRendering(HmrcFrontend, "file-upload", "uploadASingleFileWelsh", fileuploadUploadASingleFileWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "uploadAdditionalFiles", fileuploadUploadAdditionalFiles.f)
  testRendering(HmrcFrontend, "file-upload", "uploadAdditionalFilesWelsh", fileuploadUploadAdditionalFilesWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "uploaded", fileuploadUploaded.f)
  testRendering(HmrcFrontend, "file-upload", "uploadedWelsh", fileuploadUploadedWelsh.f)
  testRendering(HmrcFrontend, "file-upload", "uploading", fileuploadUploading.f)
  testRendering(HmrcFrontend, "file-upload", "uploadingWelsh", fileuploadUploadingWelsh.f)
}
