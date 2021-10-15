package algolia.dsl

import algolia.AlgoliaDsl.{custom, formats}
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}
import algolia.objects.{CustomRequest, RequestEndpoint}
import org.json4s.native.Serialization.write

class CustomRequestTest extends AlgoliaTest {
  describe("customRequest") {
    it("custom request ") {
      custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        endpoint = RequestEndpoint.Search,
        body = Some("""{"query": "van"}""")
      )
    }
    it("should call search endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        endpoint = RequestEndpoint.Search,
        body = Some(write(Map("query" -> "van", "hitsPerPage" -> 1)))
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "indexName", "query"),
          body = Some("""{"query":"van","hitsPerPage":1}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call indexing endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "clear"),
        endpoint = RequestEndpoint.Indexing
      )).build() should be(
        HttpPayload(
          POST,
          Seq("1", "indexes", "indexName", "clear"),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should call analytics endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("2", "abtests", "42", "stop"),
        endpoint = RequestEndpoint.Analytics
      )).build() should be(
        HttpPayload(
          POST,
          Seq("2", "abtests", "42", "stop"),
          isSearch = false,
          isAnalytics = true,
          requestOptions = None
        )
      )
    }

    it("should call insights endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "events"),
        endpoint = RequestEndpoint.Insights,
        body = Some(
          write(
            Map(
              "events" -> Seq(
                Map(
                  "eventType" -> "conversion",
                  "eventName" -> "event-name",
                  "index" -> "index-name",
                  "userToken" -> "user-token",
                  "objectIDs" -> Seq("objectID"),
                  "queryID" -> "query-id"
                )
              )
            )
          )
        )
      )).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("1", "events"),
          body = Some(
            """{"events":[{"eventName":"event-name","queryID":"query-id","objectIDs":["objectID"],"index":"index-name","eventType":"conversion","userToken":"user-token"}]}"""
          ),
          isSearch = false,
          isInsights = true,
          requestOptions = None
        )
      )
    }

    it("should call personalization endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "strategies", "personalization"),
        endpoint = RequestEndpoint.Personalization,
        body = Some(
          write(
            Map(
              "eventsScoring" -> Seq(
                Map(
                  "eventName" -> "buy",
                  "eventType" -> "conversion",
                  "score" -> 10
                )
              ),
              "facetsScoring" -> Seq(
                Map(
                  "facetName" -> "brand",
                  "score" -> 10
                )
              ),
              "personalizationImpact" -> 75
            )
          )
        )
      )).build() should be(
        HttpPayload(
          verb = POST,
          path = Seq("1", "strategies", "personalization"),
          body = Some(
            """{"eventsScoring":[{"eventName":"buy","eventType":"conversion","score":10}],"facetsScoring":[{"facetName":"brand","score":10}],"personalizationImpact":75}"""
          ),
          isSearch = false,
          isPersonalization = true,
          requestOptions = None
        )
      )
    }
  }
}
