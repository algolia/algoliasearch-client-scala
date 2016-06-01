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
import algolia.responses.{GetObject, Results, TasksMultipleIndex}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GetObjectIntegrationTest extends AlgoliaTest {

  after {
    clearIndices("indexToGet")
  }

  it("should get object") {
    val create: Future[TasksMultipleIndex] = client.execute {
      batch(
        index into "indexToGet" `object` ObjectToGet("1", "toto")
      )
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToGet")

    val request: Future[GetObject] = client.execute {
      get from "indexToGet" objectId "1"
    }

    whenReady(request) { result =>
      result.as[ObjectToGet] should be(ObjectToGet("1", "toto"))
    }
  }

  it("should get multiple objects") {
    val create: Future[TasksMultipleIndex] = client.execute {
      batch(
        index into "indexToGet" `object` ObjectToGet("1", "toto"),
        index into "indexToGet" `object` ObjectToGet("2", "tata")
      )
    }

    taskShouldBeCreatedAndWaitForIt(create, "indexToGet")

    val request: Future[Results] = client.execute {
      get from "indexToGet" objectIds Seq("1", "2")
    }

    whenReady(request) { results =>
      results.as[ObjectToGet] should be(Seq(ObjectToGet("1", "toto"), ObjectToGet("2", "tata")))
    }
  }


}


case class ObjectToGet(objectID: String, name: String)
