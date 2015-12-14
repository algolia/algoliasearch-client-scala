/*
 * Copyright (c) 2015 Algolia
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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.responses._
import algolia.{AlgoliaClient, AlgoliaTest}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IndicesIntegrationTest extends AlgoliaTest {

  val client = new AlgoliaClient(applicationId, apiKey)

  final case class Obj(name: String)

  it("should create indices") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index1" document Obj("1")
    }

    whenReady(create) { result =>
      result.taskID should not be 0
    }
  }

  it("should list indices") {
    val create: Future[TasksMultipleIndex] = client.execute {
      batch(
        index into "index1" document Obj("1"),
        index into "index2" document Obj("1")
      )
    }

    whenReady(create) { result =>
      result.taskID should have size 2
    }

    //TODO remove when waitFor is implemented
    Thread.sleep(2000)

    val list: Future[Indexes] = client.indices()
    whenReady(list) { result =>
      result.items.map(_.name) should (contain("index1") and contain("index2"))
    }
  }

  it("should delete an index") {
    val create: Future[TaskIndexing] = client.execute {
      index into "indexToDelete" document Obj("1")
    }

    whenReady(create) { result =>
      result.taskID should not be 0
    }

    //TODO remove when waitFor is implemented
    Thread.sleep(2000)

    val del = client.execute {
      delete index "indexToDelete"
    }

    whenReady(del) { result =>
      result.taskID should not be 0
    }

    //TODO remove when waitFor is implemented
    Thread.sleep(2000)

    val list: Future[Indexes] = client.indices()
    whenReady(list) { result =>
      result.items.map(_.name) should not contain "indexToDelete"
    }
  }

  it("should clear index") {
    val create: Future[TaskIndexing] = client.execute {
      index into "indexToClear" document Obj("1")
    }

    whenReady(create) { result =>
      result.taskID should not be 0
    }

    //TODO remove when waitFor is implemented
    Thread.sleep(2000)

    val del = client.execute {
      clear index "indexToClear"
    }

    whenReady(del) { result =>
      result.taskID should not be 0
    }

    //TODO remove when waitFor is implemented
    Thread.sleep(2000)

    val list: Future[Search] = client.execute {
      search into "indexToClear" query ""
    }

    whenReady(list) { result =>
      result.hits should have size 0
    }
  }

}

