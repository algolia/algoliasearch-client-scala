package algolia

import algolia.AlgoliaDsl._

class QueryTest extends AlgoliaTest {

  case class Nothing()

  describe("query") {

    it("should search query") {
      val client = new AlgoliaClient("APPID", "KEY")
      //      client.search { into "toto" document Basic}
      //      client.execute {
      //        val a = query "toto" hitsPerPage 1
      //        search into "index1"
      //        search into "index2" query "toto" hitsPerPage 1
      //      }
      //
      //      client.search("index1") { query "toto" }
      //
      //      client.execute {
      //        search into "index" {
      //          query "1"
      //          query "2"
      //        }
      //      }
    }

    it("should search simple query") {
      search into Index("index") query "toto"
    }

    it("should search a query with options") {
      search into "index" query "toto" hitsPerPage 1
    }

  }

}
