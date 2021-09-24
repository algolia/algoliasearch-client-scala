package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}
import algolia.objects.RecommendationsQuery

class RecommendTest extends AlgoliaTest {

  describe("test recommend payload") {
    it("should produce valid recommendation query payload") {
      (get recommendation RecommendationsQuery(
        indexName = "products",
        model = "bought-together",
        objectID = "B018APC4LE"
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "*", "recommendations"),
          body = Some(
            "{\"indexName\":\"products\",\"model\":\"bought-together\",\"objectID\":\"B018APC4LE\",\"threshold\":10,\"maxRecommendations\":10,\"queryParameters\":{\"attributesToRetrieve\":[\"*\"]}}"
          ),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }
}
