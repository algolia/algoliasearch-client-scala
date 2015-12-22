/*
 * Copyright (c) 2015 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia

import java.util.concurrent.TimeoutException

import algolia.http.{GET, HttpPayload}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class AlgoliaClientTest extends AlgoliaTest {

  val notSoRandom = new AlgoliaRandom {
    override def shuffle(seq: Seq[String]) = seq
  }

  describe("init") {

    val apiClient = new AlgoliaClient("APPID", "APIKEY") {
      override val random = notSoRandom
    }

    it("should set user agent") {
      apiClient.userAgent should (startWith("Algolia for Scala 2.11") and include("API 1."))
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

  describe("requests") {

    val mockHttpClient: DispatchHttpClient = mock[DispatchHttpClient]
    val emptyHeaders: Map[String, String] = Map()

    case class Result(value: String)

    val timeoutRequest: Future[Result] = Future.failed(new TimeoutException())

    describe("search") {

      val apiClient = new AlgoliaClient("a", "b") {
        override val httpClient = mockHttpClient
        override val headers = emptyHeaders
        override val random = notSoRandom
      }

      val successfulRequest1: Result = Result("1")
      val successfulRequest2: Result = Result("2")
      val successfulRequest3: Result = Result("3")
      val successfulRequestDsn: Result = Result("4")

      val `4XXRequest`: Future[Result] = Future.failed(new APIClientException(404, "404"))

      val payload = HttpPayload(GET, Seq("/"), None, None)

      it("no timeout") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(successfulRequest1)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest1)
        }
      }

      it("timeout on first request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(successfulRequest2)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest2)
        }
      }

      it("timeout on first and second request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(successfulRequest3)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest3)
        }
      }

      it("timeout on first, second and third requests") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *, *) returning Future.successful(successfulRequestDsn)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequestDsn)
        }

      }

      it("timeout on all requests") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *, *) returning timeoutRequest

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[TimeoutException]
        }
      }

      it("`4XX` on first request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
        }
      }

      it("`4XX` on second request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
        }
      }

      it("`4XX` on third request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
        }
      }

      it("`4XX` on all request") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *, *) returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
        }
      }
    }

    describe("indexing") {

      val apiClient = new AlgoliaClient("a", "b") {
        override val httpClient = mockHttpClient
        override val headers = emptyHeaders
        override val random = notSoRandom
      }

      val successfulRequest: Result = Result("1")

      val payload = HttpPayload(GET, Seq("/"), None, None, isSearch = false)

      it("should use indexingHosts") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a.algolia.net", emptyHeaders, payload, *, *) returning Future.successful(successfulRequest)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"), isSearch = false))) { result =>
          result should equal(successfulRequest)
        }
      }
    }
  }

  describe("init") {

    it("should throw exception if `null` APP_ID") {
      val thrown = the[AlgoliaClientException] thrownBy new AlgoliaClient(null, "APIKEY")
      thrown.getMessage should equal("'applicationId' is probably too short: 'null'")
    }

    it("should throw exception if `` APP_ID") {
      val thrown = the[AlgoliaClientException] thrownBy new AlgoliaClient("", "APIKEY")
      thrown.getMessage should equal("'applicationId' is probably too short: ''")
    }

    it("should throw exception if `null` APIKEY") {
      val thrown = the[AlgoliaClientException] thrownBy new AlgoliaClient("APP_ID", null)
      thrown.getMessage should equal("'apiKey' is probably too short: 'null'")
    }

    it("should throw exception if `` APIKEY") {
      val thrown = the[AlgoliaClientException] thrownBy new AlgoliaClient("APP_ID", "")
      thrown.getMessage should equal("'apiKey' is probably too short: ''")
    }

    it("should not throw exception if all good") {
      new AlgoliaClient("APP_ID", "APIKEY")
    }

  }
}
