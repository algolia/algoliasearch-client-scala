package algolia

import algolia.AlgoliaDsl.copy
import algolia.http.{POST, HttpPayload}

class CopyIndexTest extends AlgoliaTest {

  describe("copy") {

    it("copy index") {
      copy index "toto" to "tata"
    }

    it("should call API") {
      (copy index "toto" to "tata").build() should be(HttpPayload(POST, Seq("1", "indexes", "toto", "operation"), body = Some("{\"operation\":\"copy\",\"destination\":\"tata\"}")))
    }
  }

}
