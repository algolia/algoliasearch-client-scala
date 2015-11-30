package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{GET, HttpPayload}

class GetObjectTest extends AlgoliaTest {

  describe("get object") {

    it("should get object objectId before") {
      get objectId "myId" from "test"
    }

    it("should get object from before") {
      get from "test" objectId "myId"
    }

    it("should get less DSL") {
      get("myId") from "test"
    }

    it("should get less DSL with alias index") {
      get("myId") index "test"
    }

    it("should get less DSL from before") {
      from("test") objectId "test"
    }

    it("should get less DSL from get") {
      from("test") get "test"
    }

    it("should get object alias index") {
      from index "test" objectId "myId"
    }

    it("should get with \"/\"") {
      get / "test" / "myId"
    }

    it("should call API") {
      (get / "test" / "myid").build() eq HttpPayload(GET, Seq("1", "indexes", "test", "myId"))
    }

  }

}
