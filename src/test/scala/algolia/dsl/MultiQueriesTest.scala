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
import algolia.http._
import algolia.objects.{MultiQueries, Query}

class MultiQueriesTest extends AlgoliaTest {

  describe("multi queries") {

    it("should do multi queries") {
      multiQueries(
        search into "indexName" query Query(),
        search into "indexName2" query Query()
      ) strategy MultiQueries.Strategy.stopIfEnoughMatches
    }

    it("should call the API") {
      val m = multiQueries(
        search into "indexName1" query Query(query = Some("a")),
        search into "indexName2" query Query(query = Some("b"))
      ) strategy MultiQueries.Strategy.stopIfEnoughMatches

      m.build() should be(
        HttpPayload(
          POST,
          List("1", "indexes", "*", "queries"),
          queryParameters = Some(Map("strategy" -> "stopIfEnoughMatches")),
          body = Some(
            """{"requests":[{"indexName":"indexName1","params":"query=a"},{"indexName":"indexName2","params":"query=b"}]}"""),
          isSearch = true,
          requestOptions = None
        )
      )
    }
  }

}
