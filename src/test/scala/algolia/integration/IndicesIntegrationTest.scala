/*
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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.objects.Query
import algolia.responses._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps

class IndicesIntegrationTest extends AlgoliaTest {

  after {
    clearIndices(
      "index1",
      "index2",
      "indexToDelete",
      "indexToClear",
      "indexToCopy_before",
      "indexToCopy_after",
      "indexToMove_before",
      "indexToMove_after"
    )
  }

  it("should create indices") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index1" `object` Obj("1")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index1")
  }

  it("should list indices") {
    val create: Future[TasksMultipleIndex] = client.execute {
      batch(
        index into "index1" `object` Obj("1"),
        index into "index2" `object` Obj("1")
      )
    }

    taskShouldBeCreatedAndWaitForIt(create, "index2")

    val indices: Future[Indices] = client.execute {
      list indices
    }
    whenReady(indices) { result =>
      result.items.map(_.name) should (contain("index1") and contain("index2"))
    }
  }

  it("should delete an index") {
    val create: Future[TaskIndexing] = client.execute {
      index into "indexToDelete" `object` Obj("1")
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToDelete")

    val del = client.execute {
      delete index "indexToDelete"
    }

    taskShouldBeCreatedAndWaitForIt(del, "indexToDelete")

    val indices: Future[Indices] = client.execute {
      list indices
    }
    whenReady(indices) { result =>
      result.items.map(_.name) should not contain "indexToDelete"
    }
  }

  it("should clear index") {
    val create: Future[TaskIndexing] = client.execute {
      index into "indexToClear" `object` Obj("1")
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToClear")

    val del = client.execute {
      clear index "indexToClear"
    }

    taskShouldBeCreatedAndWaitForIt(del, "indexToClear")

    val list: Future[SearchResult] = client.execute {
      search into "indexToClear" query Query(query = Some(""))
    }

    whenReady(list) { result =>
      result.hits should have size 0
    }
  }

  it("should copy index") {
    val del = client.execute {
      clear index "indexToCopy_before"
    }

    taskShouldBeCreatedAndWaitForIt(del, "indexToCopy_before")

    val create: Future[TaskIndexing] = client.execute {
      index into "indexToCopy_before" `object` Obj("1")
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToCopy_before")

    val copying = client.execute {
      copy index "indexToCopy_before" to "indexToCopy_after"
    }

    taskShouldBeCreatedAndWaitForIt(copying, "indexToCopy_after")

    val list: Future[SearchResult] = client.execute {
      search into "indexToCopy_before" query Query(query = Some(""))
    }

    whenReady(list) { result =>
      result.hits should have size 1
    }
  }

  it("should move index") {
    val create: Future[TaskIndexing] = client.execute {
      index into "indexToMove_before" `object` Obj("1")
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToMove_before")

    val copying: Future[Task] = client.execute {
      move index "indexToMove_before" to "indexToMove_after"
    }

    taskShouldBeCreatedAndWaitForIt(copying, "indexToMove_after")

    val indices: Future[Indices] = client.execute {
      list indices
    }

    whenReady(indices) { result =>
      result.items.map(_.name) should (contain("indexToMove_after") and not contain "indexToMove_before")
    }
  }

}

case class Obj(name: String)
