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

package algolia.http

import algolia.{AlgoliaHttpClient, AlgoliaTest}

class HttpPayloadTest extends AlgoliaTest {

  describe("HttpVerb") {

    it("should GET be 'GET'") {
      GET.toString should be("GET")
    }

    it("should POST be 'POST'") {
      POST.toString should be("POST")
    }

    it("should PUT be 'PUT'") {
      PUT.toString should be("PUT")
    }

    it("should DELETE be 'DELETE'") {
      DELETE.toString should be("DELETE")
    }

  }

  val dnsNameResolver = AlgoliaHttpClient().dnsNameResolver

  describe("HttpPayload request builder") {

    val defaultPayload = HttpPayload(
      GET,
      Seq("1", "indexes"),
      None,
      None,
      isSearch = true
    )

    it("should set the URI") {
      defaultPayload("https://algolia.com", Map.empty, dnsNameResolver).getUrl should be("https://algolia.com/1/indexes")
    }

    it("should set the headers") {
      defaultPayload("https://algolia.com", Map("header" -> "value"), dnsNameResolver).getHeaders.entries().toString should be("[header=value]")
    }

    it("should set the dns timeout") {
      defaultPayload("https://algolia.com", Map("header" -> "value"), dnsNameResolver).getNameResolver should be(dnsNameResolver)
    }

    it("should set the parameters if Some") {
      val parametersPayload = defaultPayload.copy(queryParameters = Some(Map("param" -> "value")))
      val params = parametersPayload("https://algolia.com", Map.empty, dnsNameResolver).getQueryParams
      params should have size 1
      params.get(0).getName should be("param")
      params.get(0).getValue should be("value")
    }

    it("should not set the parameters if None") {
      defaultPayload("https://algolia.com", Map.empty, dnsNameResolver).getQueryParams should be(empty)
    }

    it("should set the body if Some") {
      val bodyPayload = defaultPayload.copy(body = Some("{}"))
      bodyPayload("https://algolia.com", Map.empty, dnsNameResolver).getStringData should be("{}")
    }

    it("should not set the body if None") {
      defaultPayload("https://algolia.com", Map.empty, dnsNameResolver).getByteData should be(null)
    }

  }

}
