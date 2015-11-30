package algolia

import algolia.AlgoliaDsl._
import algolia.http.{POST, HttpPayload}

class IndexTest extends AlgoliaTest {

  case class BasicObject(name: String, age: Int)

  describe("index") {

    describe("without objectId") {

      it("should index case class") {
        index into "toto" document BasicObject("algolia", 2)
      }

      it("should index objects") {
//        index into "toto" documents Seq(BasicObject("algolia", 2))
      }

    }

    describe("with objectId") {

      it("should index case class") {
        index into "toto" objectId "1" document BasicObject("algolia", 2)
      }

      it("should index case class with id") {
        index into "toto" document("1", BasicObject("algolia", 2))
      }

      it("should index objects") {
//        index into "toto" documents Map("1" -> BasicObject("algolia", 2))
      }

    }

    describe("clear") {

      it("should call API") {
        clear("toto").build() should be(HttpPayload(POST, Seq("1", "indexes", "toto", "clear")))
      }

    }

  }

}
