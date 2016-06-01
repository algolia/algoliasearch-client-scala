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

package algolia.integration

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.objects.Query
import algolia.responses.TasksMultipleIndex

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class BrowseIntegrationTest extends AlgoliaTest {

  after {
    clearIndices("indexToBrowse")
  }

  before {
    val b: Future[TasksMultipleIndex] = client.execute {
      batch(
        index into "indexToBrowse" `object` Test("algolia1", 10, alien = false),
        index into "indexToBrowse" `object` Test("algolia2", 10, alien = false),
        index into "indexToBrowse" `object` Test("algolia3", 10, alien = false),
        index into "indexToBrowse" `object` Test("anything", 10, alien = false)
      )
    }

    taskShouldBeCreatedAndWaitForIt(b, "indexToBrowse")
  }

  describe("browse") {

    it("should browse and not get a cursor") {
      val s = client.execute {
        browse index "indexToBrowse"
      }

      whenReady(s) { result =>
        result.cursor shouldBe empty
        result.hits should have length 4
      }
    }

    it("should browse with query and get a cursor") {
      val s = client.execute {
        browse index "indexToBrowse" query Query(query = Some("algolia"), hitsPerPage = Some(1))
      }

      val c = whenReady(s) { result =>
        result.cursor should not be empty
        result.hits should have length 1
        result.cursor.get
      }

      val s2 = client.execute {
        browse index "indexToBrowse" from c
      }

      whenReady(s2) { result =>
        result.cursor should not be empty
        result.hits should have length 1
      }
    }

  }

}
