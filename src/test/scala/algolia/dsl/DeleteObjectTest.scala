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
import algolia.http.{DELETE, HttpPayload, POST}

class DeleteObjectTest extends AlgoliaTest {

  describe("delete") {

    it("deletes object") {
      delete from "toto" objectId "oid"
    }

    it("deletes object with inverse DSL") {
      delete objectId "oid" from "toto"
    }

    it("should call API") {
      (delete from "toto" objectId "oid").build() should be(
        HttpPayload(
          DELETE,
          Seq("1", "indexes", "toto", "oid"),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    describe("batch") {

      it("deletes objects") {
        delete from "toto" objectIds Seq("1", "2")
      }

      it("should call the API") {
        val body = """
            | {
            |   "requests":[
            |     {
            |       "indexName":"toto",
            |       "objectID":"1",
            |       "action":"deleteObject"
            |     },{
            |       "indexName":"toto",
            |       "objectID":"2",
            |       "action":"deleteObject"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        (delete from "toto" objectIds Seq("1", "2")).build() should be(
          HttpPayload(POST,
                      List("1", "indexes", "*", "batch"),
                      body = Some(body),
                      isSearch = false,
                      requestOptions = None)
        )
      }

    }
  }

}
