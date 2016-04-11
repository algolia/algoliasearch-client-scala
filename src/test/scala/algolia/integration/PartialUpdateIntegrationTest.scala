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
import algolia.responses.{GetObject, Task, TaskIndexing}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PartialUpdateIntegrationTest extends AlgoliaTest {

  after {
    clearIndices("index_partial")
  }

  it("should increment value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateObj(1, "increment")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val inc: Future[Task] = client.execute {
      increment attribute "value" by 2 ofObjectId "increment" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(inc, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "increment"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj].value should equal(3)
    }
  }

  it("should decrement value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateObj(10, "decrement")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val dec: Future[Task] = client.execute {
      decrement attribute "value" by 4 ofObjectId "decrement" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(dec, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "decrement"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj].value should equal(6)
    }
  }

  it("should add value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateArray(Seq("1"), "add")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val adding: Future[Task] = client.execute {
      add value "2" inAttribute "values" ofObjectId "add" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(adding, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "add"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("1", "2"))
    }
  }

  it("should remove value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateArray(Seq("1", "2"), "remove")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val removing: Future[Task] = client.execute {
      remove value "1" inAttribute "values" ofObjectId "remove" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(removing, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "remove"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("2"))
    }
  }

  it("should add unique value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateArray(Seq("1", "2"), "addUnique")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val addingUnique: Future[Task] = client.execute {
      addUnique value "1" inAttribute "values" ofObjectId "addUnique" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(addingUnique, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "addUnique"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("1", "2"))
    }
  }

  it("should update a value") {
    val create: Future[TaskIndexing] = client.execute {
      index into "index_partial" `object` PartialUpdateValue("value", "otherValue", "updateValue")
    }

    taskShouldBeCreatedAndWaitForIt(create, "index_partial")

    val updating: Future[Task] = client.execute {
      update attribute "value" value "otherOne" ofObjectId "updateValue" from "index_partial"
    }

    taskShouldBeCreatedAndWaitForIt(updating, "index_partial")

    val s: Future[GetObject] = client.execute {
      get from "index_partial" objectId "updateValue"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateValue] should equal(PartialUpdateValue("otherOne", "otherValue", "updateValue"))
    }
  }


}

case class PartialUpdateObj(value: Int, objectID: String)

case class PartialUpdateArray(values: Seq[String], objectID: String)

case class PartialUpdateValue(value: String, otherValue: String, objectID: String)
