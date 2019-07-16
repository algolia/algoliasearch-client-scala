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
import algolia.AlgoliaSyncHelper._
import algolia.objects.{AbstractSynonym, Query, Rule, Synonym}
import algolia.responses._
import algolia.{AlgoliaSyncHelper, AlgoliaTest}

import scala.concurrent.Future

class AlgoliaSyncHelperTest extends AlgoliaTest {

  val helper = AlgoliaSyncHelper(AlgoliaTest.client)
  val list: Seq[Value] = 1.to(100).map(i => Value(i, i.toString))
  val syns: Seq[Synonym.Synonym] =
    1.to(10).map(i => Synonym.Synonym(s"obj_$i", Seq(s"a_$i", s"b_$i")))
  val rules: Seq[Rule] = 1.to(10).map(i => generateRule(s"obj_$i"))

  before {
    val insert = AlgoliaTest.client.execute {
      index into "testBrowseSync" objects list
    }

    taskShouldBeCreatedAndWaitForIt(insert, "testBrowseSync")

    val insertSynonyms = AlgoliaTest.client.execute {
      save synonyms syns inIndex "testBrowseSync"
    }

    taskShouldBeCreatedAndWaitForIt(insertSynonyms, "testBrowseSync")

    val insertRules = AlgoliaTest.client.execute {
      save rules rules inIndex "testBrowseSync"
    }

    taskShouldBeCreatedAndWaitForIt(insertRules, "testBrowseSync")
  }

  after {
    clearIndices("testBrowseSync")
  }

  describe("browse") {

    val query = Query(hitsPerPage = Some(10))

    it("should browse in sync") {

      val res: Iterator[Seq[Value]] =
        helper.browse[Value]("testBrowseSync", query)

      res should have size 10
      forAll(res.toIterable) { r =>
        r should have size 10
        forAll(r) { v =>
          v.int should (be >= 1 and be <= 100)
        }
      }
    }

  }

  describe("delete by query") {

    val query = Query(filters = Some("int > 10"))

    it("should delete with a query") {
      val delete = helper.deleteByQuery[Value]("testBrowseSync", query)
      val tasksToWait = whenReady(delete) { res =>
        Future.traverse(res)(i => AlgoliaTest.client.execute(waitFor task i from "testBrowseSync"))
      }

      whenReady(tasksToWait) { res =>
        res
      }

      val res: Iterator[Seq[Value]] =
        helper.browse[Value]("testBrowseSync", query)

      res should have size 1
      forAll(res.toIterable) { r =>
        r should have size 10
        forAll(r) { v =>
          v.int should (be >= 1 and be <= 10)
        }
      }
    }

  }

  describe("export synonyms") {

    it("should export synonyms") {
      val list: List[AbstractSynonym] = helper.exportSynonyms("testBrowseSync").toList.flatten

      list should have size 10
      forAll(list) { r =>
        r.objectID should startWith("obj_")
      }
    }

  }

  describe("export rules") {

    it("should export rules") {
      val list: List[Rule] = helper.exportRules("testBrowseSync").toList.flatten

      list should have size 10
      forAll(list) { r =>
        r.objectID should startWith("obj_")
      }
    }

  }

}

case class Value(int: Int, objectID: String) extends ObjectID
