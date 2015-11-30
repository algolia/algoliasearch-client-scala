package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}

class ClearIndexTest extends AlgoliaTest {

  describe("clear") {

    it("clears index") {
      clear index "toto"
    }

    it("should call API") {
      (clear index "toto").build() should be(HttpPayload(POST, Seq("1", "indexes", "toto", "clear")))
    }
  }

}
