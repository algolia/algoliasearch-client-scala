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
import algolia.http._
import algolia.objects.{Acl, ApiKey}
import org.json4s.Formats
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization._

class ApiKeysTest extends AlgoliaTest {

  implicit val formats: Formats = org.json4s.DefaultFormats + new AclSerializer

  describe("global keys") {

    describe("get a key") {
      it("should get a key") {
        get key "keyName"
      }

      it("should call the API") {
        (get key "keyName").build() should be(
          HttpPayload(
            GET,
            List("1", "keys", "keyName"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("get all keys") {
      it("should get all keys") {
        get.allKeys()
      }

      it("should call the API") {
        get.allKeys().build() should be(
          HttpPayload(
            GET,
            List("1", "keys"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("add a key") {
      it("should add a key") {
        add key ApiKey()
      }

      it("should call the API") {
        (add key ApiKey(validity = Some(10))).build() should be(
          HttpPayload(
            POST,
            List("1", "keys"),
            body = Some("""{"validity":10}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("delete a key") {
      it("should a key") {
        delete key "keyName"
      }

      it("should call the API") {
        (delete key "keyName").build() should be(
          HttpPayload(
            DELETE,
            List("1", "keys", "keyName"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("update key") {
      it("should update a key") {
        update key "keyName" `with` ApiKey()
      }

      it("should call the API") {
        (update key "keyName" `with` ApiKey(validity = Some(10)))
          .build() should be(
          HttpPayload(
            PUT,
            List("1", "keys", "keyName"),
            body = Some("""{"validity":10}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }
  }

  describe("index keys") {

    describe("get a key") {
      it("should get a key") {
        get key "keyName" from "indexName"
      }

      it("should call the API") {
        (get key "keyName" from "indexName").build() should be(
          HttpPayload(
            GET,
            List("1", "indexes", "indexName", "keys", "keyName"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("get all keys") {
      it("should get all keys") {
        get allKeysFrom "indexName"
      }

      it("should call the API") {
        (get allKeysFrom "indexName").build() should be(
          HttpPayload(
            GET,
            List("1", "indexes", "indexName", "keys"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("add a key") {
      it("should add a key") {
        add key ApiKey() to "indexName"
      }

      it("should call the API") {
        (add key ApiKey(validity = Some(10)) to "indexName").build() should be(
          HttpPayload(
            POST,
            List("1", "indexes", "indexName", "keys"),
            body = Some("""{"validity":10}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("delete a key") {
      it("should a key") {
        delete key "keyName" from "indexName"
      }

      it("should call the API") {
        (delete key "keyName" from "indexName").build() should be(
          HttpPayload(
            DELETE,
            List("1", "indexes", "indexName", "keys", "keyName"),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

    describe("update key") {

      it("should update a key") {
        update key "keyName" `with` ApiKey() from "indexName"
      }

      it("should call the API") {
        (update key "keyName" `with` ApiKey(validity = Some(10)) from "indexName")
          .build() should be(
          HttpPayload(
            PUT,
            List("1", "indexes", "indexName", "keys", "keyName"),
            body = Some("""{"validity":10}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }
  }

  describe("ApiKey serialization/deserialization") {

    val json =
      """{
        |  "acl":[
        |    "search",
        |    "addObject"
        |  ]
        |}""".stripMargin

    it("should deserialize json") {
      inside(parse(json).extract[ApiKey]) {
        case a: ApiKey =>
          a.acl should be(Some(Seq(Acl.search, Acl.addObject)))
      }

    }

    it("should serialize json") {
      val a = ApiKey(
        acl = Some(Seq(Acl.search, Acl.addObject))
      )

      writePretty(a) should be(json)
    }

  }

}
