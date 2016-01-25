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
import algolia.responses._
import algolia.{AlgoliaClient, AlgoliaTest}
import org.json4s._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class IntegrationTest extends AlgoliaTest {

  val client = new AlgoliaClient(applicationId, apiKey)

  describe("search") {

    var s: Future[Search] = Future.failed(new Exception)

    before {
      s = client.execute {
        search into "test" query "a"
      }
    }

    it("should return generic object") {
      whenReady(s) { result =>
        result.hits should have length 1
        (result.hits.head \ "name").values should be("algolia")
        (result.hits.head \ "age").values should be(10)
        (result.hits.head \ "alien").values shouldBe false
      }
    }

    it("should return case class") {
      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }
    }

    it("should return a hit") {
      whenReady(s) { result =>
        result.hits should have length 1
        val hit = EnhanceTest("algolia", 10, alien = false, "563481290", Some(Map("name" -> HighlightResult("<em>a</em>lgolia", "full"))), None, None)
        result.asHit[EnhanceTest].head should be(hit)
      }
    }
  }

  describe("indexing") {

    it("should index a document") {
      val indexing: Future[TaskIndexing] = client.execute {
        index into "toto" `object` Test("test", 1, alien = true)
      }

      whenReady(indexing) { result =>
        result.createdAt should not be empty
        result.objectID should not be empty
      }
    }

    it("should index a document with an objectID") {
      val indexing: Future[TaskIndexing] = client.execute {
        index into "toto" objectId "truc" `object` Test("test", 1, alien = true)
      }

      whenReady(indexing) { result =>
        result.objectID should be("truc")
      }
    }
  }

  describe("get object by id") {

    it("should get it") {
      val obj: Future[Get] = client.execute {
        get from "toto" objectId "truc"
      }

      whenReady(obj) { result =>
        result should be(
          Get(
            JObject(
              List(
                ("name", JString("test")),
                ("age", JInt(1)),
                ("alien", JBool(true)),
                ("objectID", JString("truc"))
              )
            )
          )
        )

        result.objectID should be("truc")

        result.as[Test] should be(Test("test", 1, alien = true))
      }
    }
  }

  describe("batches") {

    it("should insert in batch") {
      val docs = Map(
        "1" -> Test("1", 1, alien = false),
        "2" -> Test("2", 2, alien = false),
        "3" -> Test("3", 3, alien = false)
      )

      val result: Future[TasksSingleIndex] = client.execute {
        index into "toto" objects docs
      }

      whenReady(result) { result =>
        result.objectIDs should equal(Seq("1", "2", "3"))
      }
    }

    it("should insert in batch with batch DSL") {
      val result: Future[TasksMultipleIndex] = client.execute {
        batch(
          index into "toto" objectId "4" `object` Test("4", 4, alien = true),
          index into "toto" objectId "5" `object` Test("5", 5, alien = true),
          index into "toto" objectId "6" `object` Test("6", 6, alien = true)
        )
      }

      whenReady(result) { result =>
        result.objectIDs should equal(Seq("4", "5", "6"))
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
