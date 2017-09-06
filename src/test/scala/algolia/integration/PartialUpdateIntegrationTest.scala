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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.responses._

import scala.concurrent.Future

class PartialUpdateIntegrationTest extends AlgoliaTest {

  val indexName = "index_partial"

  after {
    clearIndices(indexName)
  }

  it("should increment value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateObj(1, "increment")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val inc: Future[Task] = client.execute {
      increment attribute "value" by 2 ofObjectId "increment" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(inc, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "increment"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj].value should equal(3)
    }
  }

  it("should decrement value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateObj(10, "decrement")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val dec: Future[Task] = client.execute {
      decrement attribute "value" by 4 ofObjectId "decrement" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(dec, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "decrement"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj].value should equal(6)
    }
  }

  it("should add value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateArray(Seq("1"), "add")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val adding: Future[Task] = client.execute {
      add value "2" inAttribute "values" ofObjectId "add" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(adding, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "add"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("1", "2"))
    }
  }

  it("should remove value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateArray(Seq("1", "2"), "remove")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val removing: Future[Task] = client.execute {
      remove value "1" inAttribute "values" ofObjectId "remove" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(removing, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "remove"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("2"))
    }
  }

  it("should add unique value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateArray(Seq("1", "2"), "addUnique")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val addingUnique: Future[Task] = client.execute {
      addUnique value "1" inAttribute "values" ofObjectId "addUnique" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(addingUnique, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "addUnique"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateArray].values should equal(Seq("1", "2"))
    }
  }

  it("should update a value") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateValue("value", "otherValue", "updateValue")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val updating: Future[Task] = client.execute {
      update attribute "value" value "otherOne" ofObjectId "updateValue" from indexName
    }

    taskShouldBeCreatedAndWaitForIt(updating, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "updateValue"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateValue] should equal(
        PartialUpdateValue("otherOne", "otherValue", "updateValue"))
    }
  }

  it("should update a value in a batch") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateObj(1, "batch_update")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val updating: Future[TasksMultipleIndex] = client.execute {
      batch(
        update attribute "value" value 2 ofObjectId "batch_update" from indexName
      )
    }

    taskShouldBeCreatedAndWaitForIt(updating, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "batch_update"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj] should equal(PartialUpdateObj(2, "batch_update"))
    }

  }

  it("should partial update") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateObj(1, "batch_update")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val updating: Future[Task] = client.execute {
      partialUpdate from indexName `object` PartialUpdateObjWithOtherFields(2,
                                                                            "toto",
                                                                            "batch_update")
    }

    taskShouldBeCreatedAndWaitForIt(updating, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "batch_update"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObjWithOtherFields] should equal(
        PartialUpdateObjWithOtherFields(2, "toto", "batch_update"))
    }

  }

  it("should update an object in a batch") {
    val create: Future[TaskIndexing] = client.execute {
      index into indexName `object` PartialUpdateObj(1, "batch_update")
    }

    taskShouldBeCreatedAndWaitForIt(create, indexName)

    val updating: Future[TasksMultipleIndex] = client.execute {
      batch(
        partialUpdate from indexName `object` PartialUpdateObjWithOtherFields(2,
                                                                              "toto",
                                                                              "batch_update")
      )
    }

    taskShouldBeCreatedAndWaitForIt(updating, indexName)

    val s: Future[GetObject] = client.execute {
      get from indexName objectId "batch_update"
    }

    whenReady(s) { get =>
      get.as[PartialUpdateObj] should equal(PartialUpdateObj(2, "batch_update"))
    }

  }

}

case class PartialUpdateObjWithOtherFields(value: Int, name: String, objectID: String)
    extends ObjectID

case class PartialUpdateObj(value: Int, objectID: String)

case class PartialUpdateArray(values: Seq[String], objectID: String)

case class PartialUpdateValue(value: String, otherValue: String, objectID: String)
