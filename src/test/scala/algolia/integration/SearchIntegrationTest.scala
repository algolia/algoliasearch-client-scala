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
import algolia.objects.{IndexSettings, InsideBoundingBox, InsidePolygon, Query}
import algolia.responses._
import org.json4s._

class SearchIntegrationTest extends AlgoliaTest {

  val index1: String = getTestIndexName("SearchIntegrationIndex1")
  val index2: String = getTestIndexName("SearchIntegrationIndex2")
  val index3: String = getTestIndexName("SearchIntegrationIndex3")

  override protected def beforeAll(): Unit = {
    val obj = Test("algolia", 10, alien = false)

    val res1 = AlgoliaTest.client.execute {
      index into index1 objectId "563481290" `object` obj
    }

    val res2 = AlgoliaTest.client.execute {
      index into index2 `object` Test("algolia2", 10, alien = false)
    }

    taskShouldBeCreatedAndWaitForIt(res1, index1)
    taskShouldBeCreatedAndWaitForIt(res2, index2)
  }

  override protected def afterAll(): Unit = {
    clearIndices(
      index1,
      index2,
      index3
    )
  }

  describe("search") {

    it("should return generic object") {
      val s = AlgoliaTest.client.execute {
        search into index1 query Query(
          query = Some("a"),
          explain = Some(Seq("match.alternatives"))
        )
      }

      whenReady(s) { result =>
        result.hits should have length 1
        result.explain.get.`match`.get.alternatives.get should have length 1
        (result.hits.head \ "name").values should be("algolia")
        (result.hits.head \ "age").values should be(10)
        (result.hits.head \ "alien").values shouldBe false
      }
    }

    it("should return case class") {
      val s = AlgoliaTest.client.execute {
        search into index1 query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        result.as[Test].head should be(Test("algolia", 10, alien = false))
      }
    }

    it("should return a hit") {
      val s = AlgoliaTest.client.execute {
        search into index1 query Query(query = Some("a"))
      }

      whenReady(s) { result =>
        result.hits should have length 1
        val hit = EnhanceTest(
          "algolia",
          10,
          alien = false,
          "563481290",
          Some(
            Map(
              "name" -> HighlightResult(
                "<em>a</em>lgolia",
                "full",
                Seq("a"),
                fullyHighlighted = Some(false)
              ),
              "age" -> HighlightResult(
                "10",
                "none",
                Seq.empty,
                fullyHighlighted = None
              )
            )
          ),
          None,
          None,
          None
        )
        result.asHit[EnhanceTest].head should be(hit)
      }
    }

    it("should be able to search on insideBoundingBox") {
      val box = Seq(
        InsideBoundingBox("1", "2", "3", "4"),
        InsideBoundingBox("5", "6", "7", "8")
      )
      val s = AlgoliaTest.client.execute {
        search into index1 query Query(
          query = Some("a"),
          insideBoundingBox = Some(box)
        )
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }
    }

    it("should be able to search on insidePolygon") {
      val polygon = Seq(
        InsidePolygon("1", "2", "3", "4", "5", "6"),
        InsidePolygon("7", "8", "9", "10", "11", "12")
      )
      val s = AlgoliaTest.client.execute {
        search into index1 query Query(
          query = Some("a"),
          insidePolygon = Some(polygon)
        )
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }
    }

    it("should validate mapping of SearchResult") {
      val s = AlgoliaTest.client.execute {
        search into index1 query
          Query(query = Some("a"), offset = Some(25), length = Some(10))
      }

      whenReady(s) { result =>
        result.hits should be(empty)
      }

    }
  }

  describe("multiple queries") {

    it("should return generic object") {
      val s = AlgoliaTest.client.execute {
        multipleQueries(
          search into index1 query Query(query = Some("a")),
          search into index2 query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          (result.hits.head \ "name").values.toString should startWith(
            "algolia"
          )
          (result.hits.head \ "age").values should be(10)
          (result.hits.head \ "alien").values shouldBe false
        }
      }
    }

    it("should return case class") {
      val s = AlgoliaTest.client.execute {
        multipleQueries(
          search into index1 query Query(query = Some("a")),
          search into index2 query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          result.hits should have length 1
          result.as[Test].head shouldBe a[Test]
        }
      }
    }

    it("should return a hit") {
      val s = AlgoliaTest.client.execute {
        multipleQueries(
          search into index1 query Query(query = Some("a")),
          search into index2 query Query(query = Some("a"))
        )
      }

      whenReady(s) { r =>
        r.results should have length 2
        forAll(r.results) { result =>
          result.hits should have length 1
          result.asHit[EnhanceTest].head shouldBe a[EnhanceTest]
        }
      }
    }
  }

  describe("search in facet") {

    it("should search in facets") {
      val change = AlgoliaTest.client.execute {
        setSettings of index3 `with` IndexSettings(
          attributesForFaceting = Some(Seq("searchable(series)"))
        )
      }

      taskShouldBeCreatedAndWaitForIt(change, index3)

      val add = AlgoliaTest.client.execute {
        index into index3 objects Seq(
          Character("snoopy", "Peanuts"),
          Character("woodstock", "Peanuts"),
          Character("Calvin", "Calvin & Hobbes")
        )
      }
      taskShouldBeCreatedAndWaitForIt(add, index3)

      val facetSearch = AlgoliaTest.client.execute {
        search into index3 facet "series" values "Peanuts"
      }

      whenReady(facetSearch) { r =>
        r.facetHits
      }
    }

  }

}

case class Test(name: String, age: Int, alien: Boolean)

case class EnhanceTest(
    name: String,
    age: Int,
    alien: Boolean,
    objectID: String,
    _highlightResult: Option[Map[String, HighlightResult]],
    _snippetResult: Option[Map[String, SnippetResult]],
    _rankingInfo: Option[RankingInfo],
    _distinctSeqID: Option[Integer]
) extends Hit

case class Character(name: String, series: String)
