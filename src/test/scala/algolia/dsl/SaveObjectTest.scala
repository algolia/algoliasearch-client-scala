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
import algolia.http.{HttpPayload, POST, PUT}

class SaveObjectTest extends AlgoliaTest {

  case class BasicObject(name: String, age: Int)
  case class BasicObjectWithObjectID(name: String, age: Int, objectID: String)

  describe("index") {

    describe("without objectId") {

      it("should index case class") {
        index into "toto" `object` BasicObject("algolia", 2)
      }

      it("should call API") {
        (index into "toto" `object` BasicObject("algolia", 2))
          .build() should be(
          HttpPayload(
            POST,
            List("1", "indexes", "toto"),
            body = Some("{\"name\":\"algolia\",\"age\":2}"),
            isSearch = false,
            requestOptions = None
          )
        )
      }

      describe("batch") {

        it("should index case classes") {
          index into "toto" objects Seq(BasicObject("algolia", 2))
        }

        it("without objectID should call API ") {
          (index into "toto" objects Seq(BasicObject("algolia", 2)))
            .build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some(
                "{\"requests\":[{\"body\":{\"name\":\"algolia\",\"age\":2},\"action\":\"addObject\"}]}"),
              isSearch = false,
              requestOptions = None
            )
          )
        }

        it("with objectID should call API ") {
          (index into "toto" objects Seq(BasicObjectWithObjectID("algolia", 2, "id")))
            .build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some(
                "{\"requests\":[{\"body\":{\"name\":\"algolia\",\"age\":2,\"objectID\":\"id\"},\"action\":\"updateObject\"}]}"),
              isSearch = false,
              requestOptions = None
            )
          )
        }
      }
    }

    describe("with objectId") {

      it("should index case class") {
        index into "toto" objectId "1" `object` BasicObject("algolia", 2)
      }

      it("should index case class with id") {
        index into "toto" `object` ("1", BasicObject("algolia", 2))
      }

      it("should call API") {
        (index into "toto" objectId "1" `object` BasicObject("algolia", 2))
          .build() should be(
          HttpPayload(
            PUT,
            List("1", "indexes", "toto", "1"),
            body = Some("{\"name\":\"algolia\",\"age\":2}"),
            isSearch = false,
            requestOptions = None
          )
        )
      }

      describe("batch") {

        it("should index case classes") {
          index into "toto" objects Map("1" -> BasicObject("algolia", 2))
        }

        it("should call API") {
          (index into "toto" objects Map("1" -> BasicObject("algolia", 2)))
            .build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some(
                "{\"requests\":[{\"body\":{\"objectID\":\"1\",\"name\":\"algolia\",\"age\":2},\"action\":\"updateObject\"}]}"),
              isSearch = false,
              requestOptions = None
            )
          )
        }
      }
    }
  }
}
