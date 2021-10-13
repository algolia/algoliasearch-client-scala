package algolia.dsl

import algolia.AlgoliaDsl.{custom, formats}
import algolia.AlgoliaTest
import algolia.http.{HttpPayload, POST}
import algolia.objects.{CustomRequest, RequestEndpoint}
import org.json4s.native.Serialization.write

class CustomRequestTest extends AlgoliaTest {
  describe("customRequest") {
    it("should call search endpoint") {
      custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        requestEndpoint = RequestEndpoint.Search,
        body = Some("""{"query": "van"}""")
      )
    }
    it("should call indexing endpoint") {
      (custom request CustomRequest(
        verb = POST,
        path = Seq("1", "indexes", "indexName", "query"),
        requestEndpoint = RequestEndpoint.Search,
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
  }
}
