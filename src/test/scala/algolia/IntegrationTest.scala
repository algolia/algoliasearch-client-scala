package algolia

import algolia.AlgoliaDsl._
import algolia.responses.{Indexes, Search}

import scala.concurrent.Future

class IntegrationTest extends AlgoliaTest {

  val client = new AlgoliaClient(applicationId, apiKey)

  describe("indexes") {

    it("should get a response") {
      val indices: Future[Indexes] = client.indexes()

      whenReady(indices) { result =>
        result.nbPages should equal(1)
        result.items should have size 1
        result.items.head.name should be("test")
      }
    }
  }

  final case class Test(name: String,
                        age: Int,
                        alien: Boolean)

  describe("search") {
    
    it("should search with a query") {
      val s: Future[Search] = client.execute {
        search into Index("test") query "a"
      }

      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }

    }
  }

}
