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

package uk.gov.hmrc.govukfrontend.views

import com.googlecode.htmlcompressor.compressor.HtmlCompressor
import com.googlecode.htmlcompressor.compressor.HtmlCompressor.ALL_TAGS
import org.jsoup.parser.Parser

import org.jsoup.Jsoup
import org.jsoup.helper.W3CDom

import java.io.StringWriter
import javax.xml.transform.TransformerFactory._
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

trait PreProcessor {

  private lazy val compressor = new HtmlCompressor()

  /** *
    * Compresses markup to remove irrelevant whitespace differences.
    *
    * @param html
    * @return
    */
  def parseAndCompressHtml(html: String): String = {
    compressor.setRemoveIntertagSpaces(true)
    compressor.setRemoveMultiSpaces(true)
    compressor.setRemoveSurroundingSpaces(ALL_TAGS)
    compressor.compress(Parser.unescapeEntities(html, false))
  }

  /** *
    * Function to pre-process the markup before comparing.
    *
    * @param html
    * @return String
    */
  def preProcess(html: String): String = parseAndCompressHtml(html: String)

  def normaliseHtml(html: String): String = {
    val w3cDom      = new W3CDom().fromJsoup(Jsoup.parse(html))
    val writer      = new StringWriter()
    val transformer = newInstance().newTransformer()
    transformer.transform(new DOMSource(w3cDom), new StreamResult(writer))
    writer.toString
  }
}
