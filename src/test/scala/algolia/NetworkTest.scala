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

import algolia.AlgoliaDsl._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class NetworkTest extends AlgoliaTest {

  describe("timeout on DNS resolution") {

    val apiClient = new AlgoliaClient(applicationId, apiKey) {
      override val httpClient: AlgoliaHttpClient = AlgoliaHttpClient(
        AlgoliaClientConfiguration.default.copy(dnsTimeoutMs = 1000)
      )

      override lazy val hostsStatuses =
        HostsStatuses(
          AlgoliaClientConfiguration.default,
          utils,
          Seq(
            s"https://scala-dsn.algolia.biz", //Special domain that timeout on DNS resolution
            s"https://$applicationId-1.algolianet.com"
          ),
          indexingHosts
        )
    }

    it("should answer within 3 seconds") {
      val result = apiClient.execute {
        list.indices
      }

      result.isReadyWithin(3.seconds) should be(true)
    }

    it("should get a result") {
      val result = apiClient.execute {
        list.indices
      }

      whenReady(result) { res =>
        res.items shouldNot be(null)
      }
    }
  }

  describe("TCP connect timeout") {

    val apiClient = new AlgoliaClient(applicationId, apiKey) {
      override val httpClient: AlgoliaHttpClient = AlgoliaHttpClient(
        AlgoliaClientConfiguration.default.copy(httpConnectTimeoutMs = 1000)
      )

      override lazy val hostsStatuses =
        HostsStatuses(
          AlgoliaClientConfiguration.default,
          utils,
          Seq(
            s"https://notcp-xx-1.algolianet.com", //Special domain that timeout on connect=
            s"https://$applicationId-1.algolianet.com"
          ),
          indexingHosts
        )

    }

    it("should answer within 3 seconds") {
      val result = apiClient.execute {
        list.indices
      }

      result.isReadyWithin(3.seconds) should be(true)
    }

    it("should get a result") {
      val result = apiClient.execute {
        list.indices
      }

      whenReady(result) { res =>
        res.items shouldNot be(null)
      }
    }
  }

  describe("UnknownHostException on DNS resolution") {

    val apiClient = new AlgoliaClient(applicationId, apiKey) {

      override lazy val hostsStatuses =
        HostsStatuses(
          AlgoliaClientConfiguration.default,
          utils,
          Seq(
            s"https://will-not-exists-ever.algolianet.com", //Should return UnknownHostException
            s"https://$applicationId-1.algolianet.com"
          ),
          indexingHosts
        )
    }

    it("should answer within 1 second") {
      val result = apiClient.execute {
        list.indices
      }

      result.isReadyWithin(1.second) should be(true)
    }

    it("should get a result") {
      val result = apiClient.execute {
        list.indices
      }

      whenReady(result) { res =>
        res.items shouldNot be(null)
      }
    }
  }

  describe("UnknownHostException on the last resolution") {

    val apiClient = new AlgoliaClient(applicationId, apiKey) {
      override lazy val hostsStatuses =
        HostsStatuses(
          AlgoliaClientConfiguration.default,
          utils,
          Seq(
            s"https://will-not-exists-ever.algolianet.com" //Should return UnknownHostException
          ),
          indexingHosts)
    }

    it("should fail") {
      val result = apiClient.execute {
        list.indices
      }

      whenReady(result.failed) { e =>
        e shouldBe a[AlgoliaClientException]
        e should have message "Failed on last retry"
      }
    }
  }

}
