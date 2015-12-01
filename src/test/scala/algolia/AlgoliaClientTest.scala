package algolia

import java.util.concurrent.TimeoutException

import algolia.http.{GET, HttpPayload}

import scala.concurrent.Future
import scala.reflect._

class AlgoliaClientTest extends AlgoliaTest {

  describe("init") {

    val apiClient = new AlgoliaClient("APPID", "APIKEY")

    it("should set user agent") {
      //For 2.11 AND 2.10
      apiClient.userAgent should startWith("Algolia Scala 2.1")
    }

    it("should set indexing hosts") {
      apiClient.indexingHosts should equal(Seq(
        "https://APPID-1.algolianet.com",
        "https://APPID-2.algolianet.com",
        "https://APPID-3.algolianet.com",
        "https://APPID.algolia.net"
      ))
    }

    it("should set query hosts") {
      apiClient.queryHosts should equal(Seq(
        "https://APPID-1.algolianet.com",
        "https://APPID-2.algolianet.com",
        "https://APPID-3.algolianet.com",
        "https://APPID-dsn.algolia.net"
      ))
    }

  }

  describe("request") {

    val mockHttpClient: DispatchHttpClient = mock[DispatchHttpClient]
    val emptyHeaders: Map[String, String] = Map()

    case class Result(value: String)

    val apiClient = new AlgoliaClient("a", "b") {
      override val httpClient = mockHttpClient
      override val headers = emptyHeaders
    }

    val successfulRequest1: Result = Result("1")
    val successfulRequest2: Result = Result("2")
    val successfulRequest3: Result = Result("3")
    val successfulRequestDsn: Result = Result("4")

    val timeoutRequest: Future[Result] = Future.failed(new TimeoutException())
    val `4XXRequest`: Future[Result] = Future.failed(new `4XX`(404, "404"))

    val payload = HttpPayload(GET, Seq("/"), None, None)

    it("no timeout") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning Future.successful(successfulRequest1)

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
        result should equal(successfulRequest1)
      }
    }

    it("timeout on first request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning Future.successful(successfulRequest2)

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
        result should equal(successfulRequest2)
      }
    }

    it("timeout on first and second request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-3.algolianet.com", emptyHeaders, payload, *) returning Future.successful(successfulRequest3)

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
        result should equal(successfulRequest3)
      }
    }

    it("timeout on first, second and third requests") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-3.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *) returning Future.successful(successfulRequestDsn)

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
        result should equal(successfulRequestDsn)
      }

    }

    it("timeout on all requests") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-3.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *) returning timeoutRequest

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
        e shouldBe a[TimeoutException]
      }
    }

    it("`4XX` on first request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning `4XXRequest`

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
        e shouldBe a[`4XX`]
      }
    }

    it("`4XX` on second request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning `4XXRequest`

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
        e shouldBe a[`4XX`]
      }
    }

    it("`4XX` on third request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-3.algolianet.com", emptyHeaders, payload, *) returning `4XXRequest`

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
        e shouldBe a[`4XX`]
      }
    }

    it("`4XX` on all request") {
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-1.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-2.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-3.algolianet.com", emptyHeaders, payload, *) returning timeoutRequest
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result])) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *) returning `4XXRequest`

      whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
        e shouldBe a[`4XX`]
      }
    }
  }

}
