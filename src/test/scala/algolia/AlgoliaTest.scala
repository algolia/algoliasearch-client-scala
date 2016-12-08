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

package algolia

import algolia.AlgoliaDsl._
import algolia.responses.{AlgoliaTask, TasksMultipleIndex}
import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class AlgoliaTest
    extends FunSpec
    with Matchers
    with BeforeAndAfter
    with Inspectors
    with ScalaFutures
    with Inside
    with MockFactory
    with EitherValues {

  val applicationId: String = System.getenv("APPLICATION_ID")
  val apiKey: String = System.getenv("API_KEY")
  val client = new AlgoliaClient(applicationId, apiKey) {
    override val httpClient: AlgoliaHttpClient =
      AlgoliaHttpClient(
        AlgoliaClientConfiguration(10000, 10000, 10000, 10000, 10000))
  }

  implicit val patience =
    PatienceConfig(timeout = Span(30, Seconds), interval = Span(500, Millis))

  def taskShouldBeCreatedAndWaitForIt(
      task: Future[AlgoliaTask],
      index: String)(implicit ec: ExecutionContext): Unit = {
    val t: AlgoliaTask = whenReady(task) { result =>
      result.idToWaitFor should not be 0
      result //for getting it after
    }

    val waiting = client.execute {
      waitFor task t from index maxDelay (60 * 10 * 1000) //600 seconds
    }

    whenReady(waiting) { result =>
      result.status should equal("published")
    }
  }

  def clearIndices(indices: String*): TasksMultipleIndex = {
    val del = client.execute {
      batch(indices.map { i =>
        delete index i
      })
    }

    whenReady(del) { res =>
      res
    }
  }

}
