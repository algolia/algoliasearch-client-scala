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
        index into "toto" document BasicObject("algolia", 2)
      }

      it("should call API") {
        (index into "toto" document BasicObject("algolia", 2)).build() should be(
          HttpPayload(
            POST,
            List("1", "indexes", "toto"),
            body = Some("{\"name\":\"algolia\",\"age\":2}"),
            isSearch = false
          )
        )
      }

      describe("batch") {

        it("should index case classes") {
          index into "toto" documents Seq(BasicObject("algolia", 2))
        }

        it("without objectID should call API ") {
          (index into "toto" documents Seq(BasicObject("algolia", 2))).build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some("{\"requests\":[{\"body\":{\"name\":\"algolia\",\"age\":2},\"action\":\"addObject\"}]}"),
              isSearch = false
            )
          )
        }

        it("with objectID should call API ") {
          (index into "toto" documents Seq(BasicObjectWithObjectID("algolia", 2, "id"))).build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some("{\"requests\":[{\"body\":{\"name\":\"algolia\",\"age\":2,\"objectID\":\"id\"},\"action\":\"updateObject\"}]}"),
              isSearch = false
            )
          )
        }
      }
    }

    describe("with objectId") {

      it("should index case class") {
        index into "toto" objectId "1" document BasicObject("algolia", 2)
      }

      it("should index case class with id") {
        index into "toto" document("1", BasicObject("algolia", 2))
      }

      it("should call API") {
        (index into "toto" objectId "1" document BasicObject("algolia", 2)).build() should be(
          HttpPayload(
            PUT,
            List("1", "indexes", "toto", "1"),
            body = Some("{\"name\":\"algolia\",\"age\":2}"),
            isSearch = false
          )
        )
      }

      describe("batch") {

        it("should index case classes") {
          index into "toto" documents Map("1" -> BasicObject("algolia", 2))
        }

        it("should call API") {
          (index into "toto" documents Map("1" -> BasicObject("algolia", 2))).build() should be(
            HttpPayload(
              POST,
              List("1", "indexes", "toto", "batch"),
              body = Some("{\"requests\":[{\"body\":{\"objectID\":\"1\",\"name\":\"algolia\",\"age\":2},\"action\":\"updateObject\"}]}"),
              isSearch = false
            )
          )
        }
      }
    }
  }
}
