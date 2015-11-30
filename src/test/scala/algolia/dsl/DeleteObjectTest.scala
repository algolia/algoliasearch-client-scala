package algolia.dsl

import algolia.AlgoliaTest
import algolia.AlgoliaDsl._
import algolia.http.{DELETE, HttpPayload}

class DeleteObjectTest extends AlgoliaTest {

  describe("delete") {

    it("deletes object") {
      delete from "toto" objectId "oid"
    }

    it("deletes object with DSL \"/\"") {
      delete / "toto" / "oid"
    }

    it("deletes object with inverse DSL") {
      delete objectId "oid" from "toto"
    }

    it("should call API") {
      (delete from "toto").build() should be(HttpPayload(DELETE, Seq("1", "indexes", "toto")))
    }
  }

}
