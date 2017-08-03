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

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{GET, HttpPayload, POST}

class GetObjectTest extends AlgoliaTest {

  describe("get object") {

    it("should get object objectId before") {
      get objectId "myId" from "test"
    }

    it("should get object from before") {
      get from "test" objectId "myId"
    }

    it("should call API") {
      val payload =
        HttpPayload(GET,
                    Seq("1", "indexes", "test", "myId"),
                    isSearch = true,
                    requestOptions = None)
      (get objectId "myId" from "test").build() should be(payload)
    }

  }

  describe("get multiple objects") {

    it("should get objects by ids") {
      get from "test" objectIds Seq("myId1", "myId2")
    }

    it("should call API") {
      val body = """
          |{
          | "requests":[
          |   {
          |     "indexName":"test",
          |     "objectID":"myId1"
          |   },{
          |     "indexName":"test",
          |     "objectID":"myId2"
          |   }
          |  ]
          |}
        """.stripMargin.split("\n").map(_.trim).mkString

      val payload =
        HttpPayload(POST,
                    Seq("1", "indexes", "*", "objects"),
                    body = Some(body),
                    isSearch = true,
                    requestOptions = None)
      (get from "test" objectIds Seq("myId1", "myId2")).build() should be(payload)
    }

  }

}
