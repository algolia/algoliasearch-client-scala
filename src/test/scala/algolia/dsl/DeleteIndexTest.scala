package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{DELETE, HttpPayload}

class DeleteIndexTest extends AlgoliaTest {

  describe("delete") {

    it("deletes index") {
      delete index "toto"
    }

    it("should call API") {
      (delete index "toto").build() should be(HttpPayload(DELETE, Seq("1", "indexes", "toto")))
    }
  }

}
