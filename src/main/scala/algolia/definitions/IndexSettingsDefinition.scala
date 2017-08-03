/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia.definitions

import algolia.AlgoliaDsl.ForwardToReplicas
import algolia.http.{GET, HttpPayload, PUT}
import algolia.objects.{IndexSettings, RequestOptions}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.native.Serialization._

import scala.concurrent.{ExecutionContext, Future}

case class IndexSettingsDefinition(index: String, requestOptions: Option[RequestOptions] = None)(
    implicit val formats: Formats)
    extends Definition {

  type T = IndexSettingsDefinition

  def `with`(settings: IndexSettings) =
    IndexChangeSettingsDefinition(index, settings)

  override def options(requestOptions: RequestOptions): IndexSettingsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes", index, "settings"),
      queryParameters = Some(Map("getVersion" -> "2")),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

case class IndexChangeSettingsDefinition(
    index: String,
    settings: IndexSettings,
    forward: Option[ForwardToReplicas] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  type T = IndexChangeSettingsDefinition

  override def options(requestOptions: RequestOptions): IndexChangeSettingsDefinition =
    copy(requestOptions = Some(requestOptions))

  def and(forward: ForwardToReplicas): IndexChangeSettingsDefinition =
    copy(forward = Some(forward))

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = if (forward.isDefined) {
      Some(Map("forwardToReplicas" -> "true"))
    } else {
      None
    }

    HttpPayload(
      PUT,
      Seq("1", "indexes", index, "settings"),
      body = Some(write(settings)),
      queryParameters = queryParameters,
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

trait IndexSettingsDsl {

  implicit val formats: Formats

  case object settings {

    def of(index: String) = IndexSettingsDefinition(index)

  }

  case object changeSettings {

    def of(index: String) = IndexSettingsDefinition(index)

  }

  implicit object IndexSettingsDefinitionExecutable
      extends Executable[IndexSettingsDefinition, IndexSettings] {
    override def apply(client: AlgoliaClient, settings: IndexSettingsDefinition)(
        implicit executor: ExecutionContext): Future[IndexSettings] = {
      client.request[IndexSettings](settings.build())
    }
  }

  implicit object IndexChangeSettingsDefinitionExecutable
      extends Executable[IndexChangeSettingsDefinition, Task] {
    override def apply(client: AlgoliaClient, settings: IndexChangeSettingsDefinition)(
        implicit executor: ExecutionContext): Future[Task] = {
      client.request[Task](settings.build())
    }
  }

}
