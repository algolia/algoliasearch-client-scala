/*
 * Copyright (c) 2016 Algolia
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

import algolia.AlgoliaDsl._
import algolia.definitions.WaitForTimeoutException
import algolia.http.{GET, HttpPayload}
import algolia.objects.Query
import algolia.responses.{Task, TaskStatus}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
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

    it("should set Content-Type header") {
      apiClient.headers should contain("Content-Type" -> "application/json; charset=UTF-8")
    }

    it("should set indexing hosts") {
      apiClient.indexingHosts should equal(Seq(
        "https://APPID.algolia.net",
        "https://APPID-1.algolianet.com",
        "https://APPID-2.algolianet.com",
        "https://APPID-3.algolianet.com"
      ))
    }

    it("should set query hosts") {
      apiClient.queryHosts should equal(Seq(
        "https://APPID-dsn.algolia.net",
        "https://APPID-1.algolianet.com",
        "https://APPID-2.algolianet.com",
        "https://APPID-3.algolianet.com"
      ))
    }

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

  describe("requests") {

    val mockHttpClient: DispatchHttpClient = mock[DispatchHttpClient]
    val emptyHeaders: Map[String, String] = Map()
    val timeoutRequest: Future[Result] = Future.failed(new TimeoutException())

    describe("search") {

      val apiClient = new AlgoliaClient("a", "b") {
        override lazy val httpClient = mockHttpClient
        override val headers = emptyHeaders
        override val random = notSoRandom
      }

      val payload = HttpPayload(GET, Seq("/"), None, None)
      val successfulRequestDsn: Result = Result("dsn")
      val successfulRequest1: Result = Result("1")
      val successfulRequest2: Result = Result("2")
      val successfulRequest3: Result = Result("3")

      def mockRequestDsn = (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-dsn.algolia.net", emptyHeaders, payload, *, *)
      def mockRequest1 = (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *)
      def mockRequest2 = (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *)
      def mockRequest3 = (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *)

      val `4XXRequest`: Future[Result] = Future.failed(new APIClientException(404, "404"))

      it("no timeout") {
        mockRequestDsn returning Future.successful(successfulRequestDsn)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequestDsn)
        }
      }

      it("timeout on first request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning Future.successful(successfulRequest1)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest1)
        }
      }

      it("timeout on first and second request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning Future.successful(successfulRequest2)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest2)
        }
      }

      it("timeout on first, second and third requests") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning Future.successful(successfulRequest3)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/")))) { result =>
          result should equal(successfulRequest3)
        }
      }

      it("timeout on all requests") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning timeoutRequest

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[TimeoutException]
        }
      }

      it("`4XX` on first request") {
        mockRequestDsn returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on second request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on third request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on all request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning `4XXRequest`

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"))).failed) { e =>
          e shouldBe a[APIClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }
    }

    describe("indexing") {

      val apiClient = new AlgoliaClient("a", "b") {
        override lazy val httpClient = mockHttpClient
        override val headers = emptyHeaders
        override val random = notSoRandom
      }

      val successfulRequest: Result = Result("1")

      val payload = HttpPayload(GET, Seq("/"), None, None, isSearch = false)

      it("should use indexingHosts") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a.algolia.net", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[Result], _: ExecutionContext)) expects("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(successfulRequest)

        whenReady(apiClient.request[Result](HttpPayload(http.GET, Seq("/"), isSearch = false))) { result =>
          result should equal(successfulRequest)
        }
      }
    }

    describe("failing DNS") {

      val apiClient = new AlgoliaClient(applicationId, apiKey) {
        override lazy val httpClient: DispatchHttpClient = DispatchHttpClient(201, 202, 203)

        override lazy val queryHosts: Seq[String] = Seq(
          s"https://scala-dsn.algolia.biz", //Special domain that timeout on DNS resolution
          s"https://$applicationId-1.algolianet.com"
        )
      }

      it("should answer within 1 second") {
        val result = apiClient.execute {
          list.indices
        }

        result.isReadyWithin(1.second) should be(true)
      }

      it("should get a result") {
        val start = System.currentTimeMillis()
        val result = apiClient.execute {
          list.indices
        }

        whenReady(result) { res =>
          println(s"took: ${System.currentTimeMillis() - start}")
          res.items shouldNot be(empty)
        }
      }
    }
  }

  describe("wait for") {

    val mockHttpClient: DispatchHttpClient = mock[DispatchHttpClient]
    val emptyHeaders: Map[String, String] = Map()

    val apiClient = new AlgoliaClient("a", "b") {
      override lazy val httpClient = mockHttpClient
      override val headers = emptyHeaders
      override val random = notSoRandom
    }

    val taskInProgress: TaskStatus = TaskStatus("notPublished", pendingTask = true)
    val taskPublished: TaskStatus = TaskStatus("published", pendingTask = true)
    val taskToWait: Task = Task(1L, None)

    val payload = HttpPayload(GET, Seq("1", "indexes", "toto", "task", "1"), None, None, isSearch = true)

    it("task is ready") {
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto"
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("task needs 1 retry to be ready") {
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto"
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("task needs 7 retries to be ready") {
      /*1*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*2*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*4*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*8*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*16*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto" baseDelay 1 maxDelay 17
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("future fails after too many retries") {
      /*1*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*2*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*4*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*8*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*16*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)
      /*32*/ (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(_: Manifest[TaskStatus], _: ExecutionContext)) expects("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future.successful(taskInProgress)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto" baseDelay 1 maxDelay 17
      }

      whenReady(res.failed) { e =>
        e shouldBe a[WaitForTimeoutException]
        e should have message "Waiting for task `1` on index `toto` timeout after 32ms"
      }
    }
  }

  describe("secured api keys") {

    val apiClient = new AlgoliaClient("APPID", "APIKEY")

    it("should generate a secured api key") {
      val secureApiKey = apiClient.generateSecuredApiKey("PRIVATE_API_KEY", Query(tagFilters = Some(Seq("user_42"))))
      secureApiKey should be("ZWRjMDQyY2Y0MDM1OThiZjM0MmEyM2VlNjVmOWY2YTczYzc3YWJiMzdhMjIzMDY5M2VmY2RjNmQ0MmI5NWU3NHRhZ0ZpbHRlcnM9dXNlcl80Mg==")
    }

    it("should generate a secured api key with user key") {
      val secureApiKey = apiClient.generateSecuredApiKey("PRIVATE_API_KEY", Query(tagFilters = Some(Seq("user_42"))), Some("userToken"))
      secureApiKey should be("MDc3N2VlNzkwNDY1MjRjOGFmNGJhYmVmOWI1YTM1YzYxOGQ1NWMzNjBlYWMwM2FmODY0N2VmNjMyOTE5YTAwYnRhZ0ZpbHRlcnM9dXNlcl80MiZ1c2VyVG9rZW49dXNlclRva2Vu")
    }

  }
}

case class Result(value: String)
