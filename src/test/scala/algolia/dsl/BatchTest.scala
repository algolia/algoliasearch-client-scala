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

package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}

class BatchTest extends AlgoliaTest {

  case class BasicObject(name: String, age: Int)

  describe("batch") {

    describe("index") {

      it("should index multiple objects") {
        batch(
          index into "test" `object` BasicObject("name1", 1),
//          index into "tutu" documents Seq(BasicObject("name2", 2)),
          index into "test" objectId "oid1" `object` BasicObject("name3", 3)
//          index into "tutu" documents Map("oid2" -> BasicObject("name4", 4))
        )
      }

      it("should call the API") {
        val build = batch(
          index into "test" `object` BasicObject("name1", 1),
//          index into "tutu" documents Seq(BasicObject("name2", 2)),
          index into "test" objectId "oid1" `object` BasicObject("name3", 3)
//          index into "tutu" documents Map("oid2" -> Seq(BasicObject("name4", 4)))
        ).build()

        val body =
          """
            | {
            |   "requests":[
            |     {
            |       "body":{
            |         "name":"name1",
            |         "age":1
            |       },
            |       "indexName":"test",
            |       "action":"addObject"
            |     },{
            |       "body":{
            |         "objectID":"oid1",
            |         "name":"name3",
            |         "age":3
            |       },
            |       "indexName":"test",
            |       "action":"updateObject"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        build should be(
          HttpPayload(
            POST,
            List("1", "indexes", "*", "batch"),
            body = Some(body),
            isSearch = false
          )
        )
      }

    }

    describe("clear index") {

      it("should clear multiple indices") {
        batch(
          clear index "test1",
          clear index "test2"
        )
      }

      it("should call the API") {
        val build = batch(
          clear index "test1",
          clear index "test2"
        ).build()

        val body =
          """
            | {
            |   "requests":[
            |     {
            |       "indexName":"test1",
            |       "action":"clear"
            |     },{
            |       "indexName":"test2",
            |       "action":"clear"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        build should be(
          HttpPayload(
            POST,
            List("1", "indexes", "*", "batch"),
            body = Some(body),
            isSearch = false
          )
        )
      }

    }

    describe("delete object") {

      it("should deletes multiple objects") {
        batch(
          delete from "test1" objectId "1",
          delete from "test2" objectId "2"
        )
      }

      it("should call the API") {
        val build = batch(
          delete from "test1" objectId "1",
          delete from "test2" objectId "2"
        ).build()

        val body =
          """
            | {
            |   "requests":[
            |     {
            |       "indexName":"test1",
            |       "objectID":"1",
            |       "action":"deleteObject"
            |     },{
            |       "indexName":"test2",
            |       "objectID":"2",
            |       "action":"deleteObject"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        build should be(
          HttpPayload(
            POST,
            List("1", "indexes", "*", "batch"),
            body = Some(body),
            isSearch = false
          )
        )
      }

    }

  }

}
