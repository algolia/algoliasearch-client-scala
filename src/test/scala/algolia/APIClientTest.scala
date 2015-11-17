package algolia

import java.io.InputStream

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import rapture.net.{HttpResponse, Https}

class APIClientTest extends FunSpec with BeforeAndAfter with Matchers with MockFactory {

  describe("init") {

    val apiClient = new APIClient("APP_ID", "API_KEY")

    it("should set user agent") {
      //For 2.11 AND 2.10
      apiClient.userAgent should startWith("Algolia Scala 2.1")
    }

    it("should set indexing hosts") {
      apiClient.indexingHosts should equal(Seq(
        Https / "APP_ID-1.algolianet.com",
        Https / "APP_ID-2.algolianet.com",
        Https / "APP_ID-3.algolianet.com",
        Https / "APP_ID.algolia.net"
      ))
    }

    it("should set query hosts") {
      apiClient.queryHosts should equal(Seq(
        Https / "APP_ID-1.algolianet.com",
        Https / "APP_ID-2.algolianet.com",
        Https / "APP_ID-3.algolianet.com",
        Https / "APP_ID-dsn.algolia.net"
      ))
    }

  }

  describe("get") {

    val mockHttpClient: RaptureHttpClient = stub[RaptureHttpClient]
    val emptyHeaders: Map[String, String] = Map()

    val apiClient = new APIClient("a", "b") {
      override val httpClient = mockHttpClient
      override val headers = emptyHeaders
    }

    class MockableHttpResponse extends HttpResponse(Map(), 200, mock[InputStream])

    val successfulRequest1: Option[HttpResponse] = Some(mock[MockableHttpResponse])
    val successfulRequest2: Option[HttpResponse] = Some(mock[MockableHttpResponse])
    val successfulRequest3: Option[HttpResponse] = Some(mock[MockableHttpResponse])
    val successfulRequestDsn: Option[HttpResponse] = Some(mock[MockableHttpResponse])

    val timeoutRequest: Option[HttpResponse] = None

    it("no timeout") {
      mockHttpClient.get _ when(Https / "a-1.algolianet.com", "/", emptyHeaders) returns successfulRequest1

      apiClient get "/" should equal(successfulRequest1)
    }

    it("timeout on first request") {
      mockHttpClient.get _ when(Https / "a-1.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-2.algolianet.com", "/", emptyHeaders) returns successfulRequest2

      apiClient get "/" should equal(successfulRequest2)
    }

    it("timeout on first and second request") {
      mockHttpClient.get _ when(Https / "a-1.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-2.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-3.algolianet.com", "/", emptyHeaders) returns successfulRequest3

      apiClient get "/" should equal(successfulRequest3)
    }

    it("timeout on first, second and third requests") {
      mockHttpClient.get _ when(Https / "a-1.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-2.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-3.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-dsn.algolia.net", "/", emptyHeaders) returns successfulRequestDsn

      apiClient get "/" should equal(successfulRequestDsn)
    }

    it("timeout on all requests") {
      mockHttpClient.get _ when(Https / "a-1.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-2.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-3.algolianet.com", "/", emptyHeaders) returns timeoutRequest
      mockHttpClient.get _ when(Https / "a-dsn.algolia.net", "/", emptyHeaders) returns timeoutRequest

      apiClient get "/" should equal(None)
    }

  }

}
