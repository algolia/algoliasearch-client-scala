/*
 * The MIT License (MIT)
 *
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

import scala.concurrent.{ExecutionContext, Future}

class AlgoliaClientTest extends AlgoliaTest {

  val notSoRandom = new AlgoliaUtils {
    override def shuffle(seq: Seq[String]): Seq[String] = seq
  }

  val mockHttpClient: AlgoliaHttpClient = mock[AlgoliaHttpClient]
  val emptyHeaders: Map[String, String] = Map()

  def mockedClient(utils: AlgoliaUtils = notSoRandom): AlgoliaClient =
    new AlgoliaClient("a", "b", utils = utils) {
      override val httpClient: AlgoliaHttpClient = mockHttpClient
      override val headers: Map[String, String] = emptyHeaders
    }

  describe("init") {

    val apiClient = new AlgoliaClient("APPID", "APIKEY", utils = notSoRandom)

    it("should set user agent") {
      apiClient.userAgent should (startWith("Algolia for Scala (1.") and include("; JVM (1.8") and include(
        "; Scala (2.1"))
    }

    it("should set Content-Type header") {
      apiClient.headers should contain("Content-Type" -> "application/json; charset=UTF-8")
    }

    it("should set indexing hosts") {
      apiClient.indexingHosts should equal(
        Seq(
          "https://APPID.algolia.net",
          "https://APPID-1.algolianet.com",
          "https://APPID-2.algolianet.com",
          "https://APPID-3.algolianet.com"
        ))
    }

    it("should set query hosts") {
      apiClient.queryHosts should equal(
        Seq(
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

    it("should add custom headers") {
      val client = new AlgoliaClient("APP_ID", "APIKEY", Map("my" -> "header"))
      client.headers should contain("my" -> "header")
    }

  }

  describe("requests") {
    val timeoutRequest: Future[Result] = Future.failed(new TimeoutException())

    val successfulRequestDsn: Result = Result("dsn")
    val successfulRequest1: Result = Result("1")
    val successfulRequest2: Result = Result("2")
    val successfulRequest3: Result = Result("3")

    val payload = HttpPayload(GET, Seq("/"), None, None, isSearch = true, requestOptions = None)

    def mockRequestDsn =
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[Result],
        _: ExecutionContext)) expects ("https://a-dsn.algolia.net", emptyHeaders, payload, *, *)

    def mockRequest1 =
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[Result],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *)

    def mockRequest2 =
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[Result],
        _: ExecutionContext)) expects ("https://a-2.algolianet.com", emptyHeaders, payload, *, *)

    def mockRequest3 =
      (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[Result],
        _: ExecutionContext)) expects ("https://a-3.algolianet.com", emptyHeaders, payload, *, *)

    val `4XXRequest`: Future[Result] =
      Future.failed(`4XXAPIException`(404, "404"))

    var apiClient: AlgoliaClient = null

    before {
      apiClient = mockedClient()
    }

    describe("search") {

      it("no timeout") {
        mockRequestDsn returning Future.successful(successfulRequestDsn)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequestDsn)
        }
      }

      it("timeout on first request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning Future.successful(successfulRequest1)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequest1)
        }
      }

      it("timeout on first and second request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning Future.successful(successfulRequest2)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequest2)
        }
      }

      it("timeout on first, second and third requests") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning Future.successful(successfulRequest3)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequest3)
        }
      }

      it("timeout on all requests") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning timeoutRequest

        whenReady(apiClient
          .request[Result](HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))
          .failed) { e =>
          e shouldBe a[AlgoliaClientException]
        }
      }

      it("`4XX` on first request") {
        mockRequestDsn returning `4XXRequest`

        whenReady(apiClient
          .request[Result](HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))
          .failed) { e =>
          e shouldBe a[AlgoliaClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on second request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning `4XXRequest`

        whenReady(apiClient
          .request[Result](HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))
          .failed) { e =>
          e shouldBe a[AlgoliaClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on third request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning `4XXRequest`

        whenReady(apiClient
          .request[Result](HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))
          .failed) { e =>
          e shouldBe a[AlgoliaClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }

      it("`4XX` on all request") {
        mockRequestDsn returning timeoutRequest
        mockRequest1 returning timeoutRequest
        mockRequest2 returning timeoutRequest
        mockRequest3 returning `4XXRequest`

        whenReady(apiClient
          .request[Result](HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))
          .failed) { e =>
          e shouldBe a[AlgoliaClientException]
          e should have message "Failure \"404\", response status: 404"
        }
      }
    }

    describe("indexing") {

      val successfulRequest: Result = Result("1")

      val payload = HttpPayload(GET, Seq("/"), None, None, isSearch = false, requestOptions = None)

      it("should use indexingHosts") {
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
          _: Manifest[Result],
          _: ExecutionContext)) expects ("https://a.algolia.net", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
          _: Manifest[Result],
          _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
          _: Manifest[Result],
          _: ExecutionContext)) expects ("https://a-2.algolianet.com", emptyHeaders, payload, *, *) returning timeoutRequest
        (mockHttpClient.request[Result](_: String, _: Map[String, String], _: HttpPayload)(
          _: Manifest[Result],
          _: ExecutionContext)) expects ("https://a-3.algolianet.com", emptyHeaders, payload, *, *) returning Future
          .successful(successfulRequest)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = false, requestOptions = None))) { result =>
          result should equal(successfulRequest)
        }
      }
    }

    describe("retry with state") {

      def fixNow(date: Int) = new AlgoliaUtils {
        override def shuffle(seq: Seq[String]): Seq[String] = seq

        override def now(): Long = date.toLong
      }

      import scala.collection.JavaConverters._

      it("should not reconnect to the same host twice if it fails") {
        val apiClient: AlgoliaClient = mockedClient(fixNow(1))

        mockRequestDsn returning timeoutRequest
        mockRequest1 returning Future.successful(successfulRequest1)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequest1)
        }

        apiClient.hostsStatuses.hostStatuses.asScala should be(
          Map(
            "https://a-dsn.algolia.net" -> HostStatus(up = false, 1l),
            "https://a-1.algolianet.com" -> HostStatus(up = true, 1l)
          ))

        mockRequest1 returning Future.successful(successfulRequest1)

        whenReady(
          apiClient.request[Result](
            HttpPayload(http.GET, Seq("/"), isSearch = true, requestOptions = None))) { result =>
          result should equal(successfulRequest1)
        }

        apiClient.hostsStatuses.hostStatuses.asScala should be(
          Map(
            "https://a-dsn.algolia.net" -> HostStatus(up = false, 1l),
            "https://a-1.algolianet.com" -> HostStatus(up = true, 1l)
          ))
      }

    }
  }

  describe("wait for") {
    val apiClient = mockedClient()

    val taskInProgress: TaskStatus =
      TaskStatus("notPublished", pendingTask = true)
    val taskPublished: TaskStatus = TaskStatus("published", pendingTask = true)
    val taskToWait: Task = Task(1L, None)

    val payload =
      HttpPayload(GET,
                  Seq("1", "indexes", "toto", "task", "1"),
                  None,
                  None,
                  isSearch = true,
                  requestOptions = None)

    it("task is ready") {
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto"
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("task needs 1 retry to be ready") {
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto"
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("task needs 7 retries to be ready") {
      /*1*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*2*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*4*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*8*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*16*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskPublished)

      val res: Future[TaskStatus] = apiClient.execute {
        waitFor task taskToWait from "toto" baseDelay 1 maxDelay 17
      }

      whenReady(res) { result =>
        result should equal(taskPublished)
      }
    }

    it("future fails after too many retries") {
      /*1*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*2*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*4*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*8*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*16*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)
      /*32*/
      (mockHttpClient.request[TaskStatus](_: String, _: Map[String, String], _: HttpPayload)(
        _: Manifest[TaskStatus],
        _: ExecutionContext)) expects ("https://a-1.algolianet.com", emptyHeaders, payload, *, *) returning Future
        .successful(taskInProgress)

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
      val secureApiKey = apiClient.generateSecuredApiKey("PRIVATE_API_KEY",
                                                         Query(tagFilters = Some(Seq("user_42"))))
      secureApiKey should be(
        "ZWRjMDQyY2Y0MDM1OThiZjM0MmEyM2VlNjVmOWY2YTczYzc3YWJiMzdhMjIzMDY5M2VmY2RjNmQ0MmI5NWU3NHRhZ0ZpbHRlcnM9dXNlcl80Mg==")
    }

    it("should generate a secured api key with user key") {
      val secureApiKey = apiClient.generateSecuredApiKey("PRIVATE_API_KEY",
                                                         Query(tagFilters = Some(Seq("user_42"))),
                                                         Some("userToken"))
      secureApiKey should be(
        "MDc3N2VlNzkwNDY1MjRjOGFmNGJhYmVmOWI1YTM1YzYxOGQ1NWMzNjBlYWMwM2FmODY0N2VmNjMyOTE5YTAwYnRhZ0ZpbHRlcnM9dXNlcl80MiZ1c2VyVG9rZW49dXNlclRva2Vu")
    }

  }
}

case class Result(value: String)
