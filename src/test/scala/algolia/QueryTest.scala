package algolia

import algolia.AlgoliaDsl._

class QueryTest extends AlgoliaTest {

  case class Nothing()

  describe("query") {

    it("should search simple query") {
      search into Index("index") query "toto"
    }

    it("should search a query with options") {
      search into "index" query "toto" hitsPerPage 1
    }

  }

}
