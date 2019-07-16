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

package algolia.dsl

import algolia.{AlgoliaClient, Executable}
import algolia.definitions.InsightsEventDefinition
import algolia.inputs._
import algolia.responses.InsightsEventResponse
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait SendDsl {
  implicit val formats: Formats

  case object send {

    def event(e: InsightsEvent): InsightsEventDefinition = InsightsEventDefinition(Seq(e))
    def events(e: Iterable[InsightsEvent]): InsightsEventDefinition = InsightsEventDefinition(e)

  }

  implicit object SendInsightEventExecutable
      extends Executable[InsightsEventDefinition, InsightsEventResponse] {
    override def apply(client: AlgoliaClient, query: InsightsEventDefinition)(
        implicit executor: ExecutionContext): Future[InsightsEventResponse] = {
      client.request[InsightsEventResponse](query.build())
    }
  }
}
