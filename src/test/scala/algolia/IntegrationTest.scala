package algolia

import algolia.AlgoliaDsl._
import algolia.responses._
import org.json4s._

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

  final case class EnhanceTest(name: String,
                               age: Int,
                               alien: Boolean,
                               objectID: String,
                               _highlightResult: Option[Map[String, HighlightResult]],
                               _snippetResult: Option[Map[String, SnippetResult]],
                               _rankingInfo: Option[RankingInfo]) extends Hit

  describe("search") {

    var s: Future[Search] = Future.failed(new Exception)

    before {
      s = client.execute {
        search into Index("test") query "a"
      }
    }

    it("should return generic object") {
      whenReady(s) { result =>
        result.hits should have length 1
        (result.hits.head \ "name").values should be("algolia")
        (result.hits.head \ "age").values should be(10)
        (result.hits.head \ "alien").values shouldBe false
      }
    }

    it("should return case class") {
      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }
    }

    it("should return a hit") {
      whenReady(s) { result =>
        result.hits should have length 1
        val hit = EnhanceTest("algolia", 10, alien = false, "563481290", Some(Map("name" -> HighlightResult("<em>a</em>lgolia", "full"))), None, None)
        result.asHit[EnhanceTest].head should be(hit)
      }
    }
  }

}
