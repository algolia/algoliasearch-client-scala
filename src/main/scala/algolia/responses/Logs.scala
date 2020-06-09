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

package algolia.responses

case class Log(
    timestamp: String,
    method: String,
    answer_code: String,
    query_body: String,
    answer: String,
    url: String,
    ip: String,
    query_headers: String,
    sha1: String,
    nb_api_calls: String,
    processing_time_ms: String,
    index: Option[String],
    query_nb_hits: Option[String],
    exhaustive: Option[Boolean],
    inner_queries: Seq[InnerQuery]
)

case class InnerQuery(
    index_name: String,
    query_id: Option[String],
    offset: Option[Int],
    user_token: Option[String]
)

sealed trait LogType {

  val name: String

}

case object LogType {

  case object all extends LogType {
    override val name: String = "all"
  }

  case object query extends LogType {
    override val name: String = "query"
  }

  case object build extends LogType {
    override val name: String = "build"
  }

  case object error extends LogType {
    override val name: String = "error"
  }

}

case class Logs(logs: Seq[Log])
