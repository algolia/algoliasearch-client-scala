package algolia.dsl

import algolia.AlgoliaDsl.move
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}

class MoveIndexTest extends AlgoliaTest {

  describe("move") {

    it("moves index") {
      move index "toto" to "tata"
    }

    it("should call API") {
      (move index "toto" to "tata").build() should be(HttpPayload(POST, Seq("1", "indexes", "toto", "operation"), body = Some("{\"operation\":\"move\",\"destination\":\"tata\"}")))
    }
  }
}
