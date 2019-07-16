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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.objects.Query

class DeleteByIntegrationTest extends AlgoliaTest {

  val list: Seq[Value] = 1 to 100 map (i => Value(i, i.toString))
  val testDeleteBy: String = getTestIndexName("testDeleteBy")

  before {
    val insert = AlgoliaTest.client.execute {
      index into testDeleteBy objects list
    }

    taskShouldBeCreatedAndWaitForIt(insert, testDeleteBy)
  }

  after {
    clearIndices(testDeleteBy)
  }

  describe("delete by query") {

    val query = Query(filters = Some("int > 10"))

    it("should delete with a query") {
      val d = AlgoliaTest.client.execute {
        delete from testDeleteBy by query
      }

      taskShouldBeCreatedAndWaitForIt(d, testDeleteBy)
    }

  }

}
