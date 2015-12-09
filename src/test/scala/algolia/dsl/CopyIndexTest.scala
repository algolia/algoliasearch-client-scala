package algolia.dsl

import algolia.AlgoliaDsl.copy
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}

class CopyIndexTest extends AlgoliaTest {

  describe("copy") {

    it("copy index") {
      copy index "toto" to "tata"
    }

    it("should call API") {
      (copy index "toto" to "tata").build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "toto", "operation"),
          body = Some("{\"operation\":\"copy\",\"destination\":\"tata\"}"),
          isSearch = false
        )
      )
    }
  }

}
