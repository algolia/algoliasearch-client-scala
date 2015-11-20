package algolia

import java.util.concurrent.TimeoutException

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

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

  describe("get") {

    val mockHttpClient: DispatchHttpClient = mock[DispatchHttpClient]
    val emptyHeaders: Map[String, String] = Map()
    val emptyParams: Map[String, String] = Map()

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

    it("no timeout") {
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-1.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning Future.successful(successfulRequest1)

      whenReady(apiClient.get[Result](Seq("/"))) { result =>
        result should equal(successfulRequest1)
      }
    }

    it("timeout on first request") {
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-1.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-2.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning Future.successful(successfulRequest2)

      whenReady(apiClient.get[Result](Seq("/"))) { result =>
        result should equal(successfulRequest2)
      }
    }

    it("timeout on first and second request") {
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-1.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-2.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-3.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning Future.successful(successfulRequest3)

      whenReady(apiClient.get[Result](Seq("/"))) { result =>
        result should equal(successfulRequest3)
      }
    }

    it("timeout on first, second and third requests") {
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-1.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-2.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-3.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-dsn.algolia.net", Seq("/"), emptyParams, emptyHeaders, *) returning Future.successful(successfulRequestDsn)

      whenReady(apiClient.get[Result](Seq("/"))) { result =>
        result should equal(successfulRequestDsn)
      }

    }

    it("timeout on all requests") {
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-1.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-2.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-3.algolianet.com", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest
      (mockHttpClient.get[Result](_: String, _: Seq[String], _: Map[String, String], _: Map[String, String])(_: Manifest[Result])) expects("https://a-dsn.algolia.net", Seq("/"), emptyParams, emptyHeaders, *) returning timeoutRequest

      whenReady(apiClient.get[Result](Seq("/")).failed) { e =>
        e shouldBe a [TimeoutException]
      }
    }
  }

}
