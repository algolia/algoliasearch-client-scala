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

import algolia.http.{GET, HttpPayload}
import algolia.objects.RequestOptions
import algolia.responses.{LogType, Logs}
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

case class LogsDefinition(offset: Option[Int] = None,
                          length: Option[Int] = None,
                          `type`: Option[LogType] = None,
                          requestOptions: Option[RequestOptions] = None)
    extends Definition {

  type T = LogsDefinition

  def offset(o: Int): LogsDefinition = copy(offset = Some(o))

  def length(l: Int): LogsDefinition = copy(length = Some(l))

  def `type`(t: LogType): LogsDefinition = copy(`type` = Some(t))

  override def options(requestOptions: RequestOptions): LogsDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val queryParameters = mutable.Map[String, String]()
    offset.map { o =>
      queryParameters.put("offset", o.toString)
    }
    length.map { l =>
      queryParameters.put("length", l.toString)
    }
    `type`.map { t =>
      queryParameters.put("type", t.name)
    }

    HttpPayload(
      GET,
      Seq("1", "logs"),
      queryParameters = Some(queryParameters.toMap),
      isSearch = false,
      requestOptions = requestOptions
    )
  }
}

trait LogsDsl {

  implicit val formats: Formats

  def logs() = LogsDefinition()

  implicit object LogsDefinitionExecutable extends Executable[LogsDefinition, Logs] {
    override def apply(client: AlgoliaClient, query: LogsDefinition)(
        implicit executor: ExecutionContext): Future[Logs] = {
      client.request[Logs](query.build())
    }
  }

}
