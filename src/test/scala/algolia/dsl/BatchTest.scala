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
import algolia.http.{HttpPayload, POST}

class BatchTest extends AlgoliaTest {

  case class BasicObject(name: String, age: Int)

  describe("batch") {

    describe("index") {

      it("should index multiple objects") {
        batch(
          index into "test" `object` BasicObject("name1", 1),
          index into "tutu" objects Seq(BasicObject("name2", 2)),
          index into "test" objectId "oid1" `object` BasicObject("name3", 3),
          index into "tutu" objects Map("oid2" -> BasicObject("name4", 4))
        )
      }

      it("should call the API") {
        val build = batch(
          index into "test" `object` BasicObject("name1", 1),
          index into "test" objectId "oid1" `object` BasicObject("name3", 3)
        ).build()

        val body = """
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
            isSearch = false,
            requestOptions = None
          )
        )
      }

      it("should call the API for batches of batches") {
        val build = batch(
          index into "tutu" objects Seq(BasicObject("name2", 2)),
          index into "tutu" objects Map("oid4" -> BasicObject("name4", 4))
        ).build()

        val body = """
            | {
            |   "requests":[
            |     {
            |       "body":{
            |         "name":"name2",
            |         "age":2
            |       },
            |       "indexName":"tutu",
            |       "action":"addObject"
            |     },{
            |       "body":{
            |         "objectID":"oid4",
            |         "name":"name4",
            |         "age":4
            |       },
            |       "indexName":"tutu",
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
            isSearch = false,
            requestOptions = None
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

        val body = """
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
            isSearch = false,
            requestOptions = None
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

        val body = """
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
            isSearch = false,
            requestOptions = None
          )
        )
      }

    }

    describe("partial update object") {

      it("should update fields") {
        batch(
          increment attribute "toto" by 1 from "index1" ofObjectId "myId",
          decrement attribute "toto" by 1 ofObjectId "myId" from "index2",
          add value "truc" inAttribute "toto" ofObjectId "myId" from "index3",
          remove value "truc" inAttribute "toto" ofObjectId "myId" from "index4",
          addUnique value "truc" inAttribute "toto" ofObjectId "myId" from "index5",
          increment attribute "toto" by 1 from "index6" ofObjectId "1" createIfNotExists false,
          update attribute "attr" value "newValue" ofObjectId "myId" from "index6"
        )
      }

      it("should call the API") {
        val build = batch(
          increment attribute "att1" by 1 from "index1" ofObjectId "1",
          decrement attribute "att2" by 1 ofObjectId "2" from "index2",
          add value "truc" inAttribute "att3" ofObjectId "3" from "index3",
          remove value "truc" inAttribute "att4" ofObjectId "4" from "index4",
          addUnique value "truc" inAttribute "att5" ofObjectId "5" from "index5",
          increment attribute "att6" by 1 from "index6" ofObjectId "6" createIfNotExists false,
          update attribute "att7" value "newValue" ofObjectId "7" from "index7"
        ).build()

        val body = """
            | {
            |   "requests":[
            |     {
            |       "body":{
            |         "objectID":"1",
            |         "att1":{
            |           "_operation":"Increment",
            |           "value":1
            |         }
            |       },
            |       "indexName":"index1",
            |       "action":"partialUpdateObject"
            |     },{
            |       "body":{
            |         "objectID":"2",
            |         "att2":{
            |           "_operation":"Decrement",
            |           "value":1
            |         }
            |       },
            |       "indexName":"index2",
            |       "action":"partialUpdateObject"
            |     },{
            |       "body":{
            |         "objectID":"3",
            |         "att3":{
            |           "_operation":"Add",
            |           "value":"truc"
            |         }
            |       },
            |       "indexName":"index3",
            |       "action":"partialUpdateObject"
            |     },{
            |       "body":{
            |         "objectID":"4",
            |         "att4":{
            |           "_operation":"Remove",
            |           "value":"truc"
            |         }
            |       },
            |       "indexName":"index4",
            |       "action":"partialUpdateObject"
            |     },{
            |       "body":{
            |         "objectID":"5",
            |         "att5":{
            |           "_operation":"AddUnique",
            |           "value":"truc"
            |         }
            |       },
            |       "indexName":"index5",
            |       "action":"partialUpdateObject"
            |     },{
            |       "body":{
            |         "objectID":"6",
            |         "att6":{
            |           "_operation":"Increment",
            |           "value":1
            |         }
            |       },
            |       "indexName":"index6",
            |       "action":"partialUpdateObjectNoCreate"
            |     },{
            |       "body":{
            |         "objectID":"7",
            |         "att7":"newValue"
            |       },
            |       "indexName":"index7",
            |       "action":"partialUpdateObject"
            |     }
            |   ]
            | }
          """.stripMargin.split("\n").map(_.trim).mkString

        build should be(
          HttpPayload(
            POST,
            List("1", "indexes", "*", "batch"),
            body = Some(body),
            isSearch = false,
            requestOptions = None
          )
        )
      }

    }

  }

}
