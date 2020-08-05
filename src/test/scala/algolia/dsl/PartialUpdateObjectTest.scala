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

class PartialUpdateObjectTest extends AlgoliaTest {

  describe("increment value") {

    it("should increment") {
      increment attribute "toto" ofObjectId "myId" by 1 from "index"
    }

    it("should build increment attribute payload") {
      (increment attribute "toto" ofObjectId "myId" by 1 from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"Increment","value":1}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should increment from") {
      increment from "toto" ofObjectId "myId" by 1 from "index"
    }

    it("should build increment from payload") {
      (increment from "toto" ofObjectId "myId" by 1 from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"IncrementFrom","value":1}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should increment set") {
      increment set "toto" ofObjectId "myId" by 1 from "index"
    }

    it("should build increment set payload") {
      (increment set "toto" ofObjectId "myId" by 1 from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"IncrementSet","value":1}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("decrement value") {

    it("should increment") {
      decrement attribute "toto" by 1 ofObjectId "myId" from "index"
    }

    it("should call API") {
      (decrement attribute "toto" by 1 ofObjectId "myId" from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"Decrement","value":1}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("add value") {

    it("should add value") {
      add value "truc" inAttribute "toto" ofObjectId "myId" from "index"
    }

    it("should call API") {
      (add value "truc" inAttribute "toto" ofObjectId "myId" from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"Add","value":"truc"}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("remove value") {

    it("remove add value") {
      remove value "truc" inAttribute "toto" ofObjectId "myId" from "index"
    }

    it("should call API") {
      (remove value "truc" inAttribute "toto" ofObjectId "myId" from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"Remove","value":"truc"}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("addUnique value") {

    it("should add unique value") {
      addUnique value "truc" inAttribute "toto" ofObjectId "myId" from "index"
    }

    it("should call API") {
      (addUnique value "truc" inAttribute "toto" ofObjectId "myId" from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":{"_operation":"AddUnique","value":"truc"}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("update one field") {

    it("should update one field") {
      update attribute "toto" value "truc" ofObjectId "myId" from "index"
    }

    it("should call API") {
      (update attribute "toto" value "truc" ofObjectId "myId" from "index")
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          body = Some("""{"toto":"truc"}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("create if not exists") {

    it("should do not create if no exists") {
      increment attribute "toto" ofObjectId "myId" by 1 from "index" createIfNotExists false
    }

    it("should call API") {
      (increment attribute "toto" ofObjectId "myId" by 1 from "index" createIfNotExists false)
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          queryParameters = Some(Map("createIfNotExists" -> "false")),
          body = Some("""{"toto":{"_operation":"Increment","value":1}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update") {

    it("should partial update") {
      partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      )
    }

    it("should call API") {
      (partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          queryParameters = None,
          body = Some("""{"name":"name1","age":1,"objectID":"myId"}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update create") {

    it("should partial update") {
      partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      ) createIfNotExists true
    }

    it("should call API") {
      (partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      ) createIfNotExists true)
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          queryParameters = None,
          body = Some("""{"name":"name1","age":1,"objectID":"myId"}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update no create") {

    it("should partial update") {
      partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      ) createIfNotExists false
    }

    it("should call API") {
      (partialUpdate from "index" `object` BasicObjectWithObjectID(
        "name1",
        1,
        "myId"
      ) createIfNotExists false)
        .build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "index", "myId", "partial"),
          queryParameters = Some(Map("createIfNotExists" -> "false")),
          body = Some("""{"name":"name1","age":1,"objectID":"myId"}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update objects") {

    it("should partial update") {
      partialUpdate from "index" objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )
    }

    it("should call API") {
      (partialUpdate from "index" objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "batch"),
          queryParameters = None,
          body = Some(
            """{"requests":[{"body":{"name":"name1","age":1,"objectID":"myId"},"indexName":"index","action":"partialUpdateObject"}]}"""
          ),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update objects create") {

    it("should partial update") {
      partialUpdate from "index" createIfNotExists true objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )
    }

    it("should call API") {
      (partialUpdate from "index" createIfNotExists true objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "batch"),
          queryParameters = None,
          body = Some(
            """{"requests":[{"body":{"name":"name1","age":1,"objectID":"myId"},"indexName":"index","action":"partialUpdateObject"}]}"""
          ),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

  describe("partial update objects no create") {

    it("should partial update") {
      partialUpdate from "index" createIfNotExists false objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )
    }

    it("should call API") {
      (partialUpdate from "index" createIfNotExists false objects Seq(
        BasicObjectWithObjectID("name1", 1, "myId")
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "batch"),
          queryParameters = None,
          body = Some(
            """{"requests":[{"body":{"name":"name1","age":1,"objectID":"myId"},"indexName":"index","action":"partialUpdateObjectNoCreate"}]}"""
          ),
          isSearch = false,
          requestOptions = None
        )
      )
    }

  }

}
