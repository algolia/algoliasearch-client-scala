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

package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{GET, HttpPayload}
import algolia.objects.Query

class BrowseIndexTest extends AlgoliaTest {

  describe("browse") {

    it("browses index") {
      browse index "toto" from "cursor"
    }

    it("browses index with query") {
      browse index "toto" query Query(query = Some("q"))
    }

    it("should call API") {
      (browse index "toto" from "cursor1").build() should be(
        HttpPayload(
          GET,
          Seq("1", "indexes", "toto", "browse"),
          queryParameters = Some(Map("cursor" -> "cursor1")),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call API with query") {
      (browse index "toto" query Query(query = Some("q"))).build() should be(
        HttpPayload(
          GET,
          Seq("1", "indexes", "toto", "browse"),
          queryParameters = Some(Map("query" -> "q")),
          isSearch = true,
          requestOptions = None
        )
      )
    }

    it("should call API with query and cursor") {
      (browse index "toto" query Query(query = Some("q")) from "cursor1")
        .build() should be(
        HttpPayload(
          GET,
          Seq("1", "indexes", "toto", "browse"),
          queryParameters = Some(Map("query" -> "q", "cursor" -> "cursor1")),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }

}
