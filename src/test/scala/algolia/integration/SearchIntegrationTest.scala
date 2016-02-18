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
import algolia.objects.Query
import algolia.responses.LogType.query
import algolia.responses._
import algolia.{AlgoliaClient, AlgoliaTest}
import org.json4s._

import scala.concurrent.ExecutionContext.Implicits.global

class SearchIntegrationTest extends AlgoliaTest {

  val client = new AlgoliaClient(applicationId, apiKey)

  after {
    val indices = Seq(
      "indexToSearch"
    )

    val del = client.execute {
      batch(indices.map { i => delete index i })
    }

    whenReady(del) { res => res }
  }

  describe("search") {

    before {
      val insert = client.execute {
        index into "indexToSearch" objectId "563481290" `object` Test("algolia", 10, alien = false)
      }

      taskShouldBeCreatedAndWaitForIt(client, insert, "indexToSearch")
    }

    it("should return generic object") {
      val s = client.execute {
        search into "indexToSearch" query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        (result.hits.head \ "name").values should be("algolia")
        (result.hits.head \ "age").values should be(10)
        (result.hits.head \ "alien").values shouldBe false
      }
    }

    it("should return case class") {
      val s = client.execute {
        search into "indexToSearch" query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }
    }

    it("should return a hit") {
      val s = client.execute {
        search into "indexToSearch" query Query(query = Some("a"))
      }


      whenReady(s) { result =>
        result.hits should have length 1
        val hit = EnhanceTest("algolia", 10, alien = false, "563481290", Some(Map("name" -> HighlightResult("<em>a</em>lgolia", "full"))), None, None)
        result.asHit[EnhanceTest].head should be(hit)
      }
    }
  }

}

case class Test(name: String,
                age: Int,
                alien: Boolean)

case class EnhanceTest(name: String,
                       age: Int,
                       alien: Boolean,
                       objectID: String,
                       _highlightResult: Option[Map[String, HighlightResult]],
                       _snippetResult: Option[Map[String, SnippetResult]],
                       _rankingInfo: Option[RankingInfo]) extends Hit
