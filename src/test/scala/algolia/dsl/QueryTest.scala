package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest

class QueryTest extends AlgoliaTest {

  case class Nothing()

  describe("query") {

    it("should search simple query") {
      search into "index" query "toto"
    }

    it("should search a query with options") {
      search into "index" query "toto" hitsPerPage 1
    }

  }

}
