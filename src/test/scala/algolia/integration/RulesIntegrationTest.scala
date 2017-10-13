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
import algolia.objects._
import algolia.{AlgoliaDsl, AlgoliaTest}

class RulesIntegrationTest extends AlgoliaTest {

  val indexName = "indexToRule"

  after {
    clearIndices(indexName)
  }

  describe("rules") {

    it("should save a rule") {
      val o = client.execute {
        index into indexName `object` Value(1, "1")
      }

      taskShouldBeCreatedAndWaitForIt(o, indexName)

      val res = client.execute {
        save rule generateRule("rule1") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res, indexName)

      val r = client.execute {
        get rule "rule1" from indexName
      }

      whenReady(r) { res =>
        res.objectID should be("rule1")
        res.description shouldBe Some("rule1")
      }
    }

    it("should search rules") {
      val o = client.execute {
        index into indexName `object` Value(1, "1")
      }

      taskShouldBeCreatedAndWaitForIt(o, indexName)

      val res1 = client.execute {
        save rule generateRule("rule1") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = client.execute {
        save rule generateRule("rule2") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val s = client.execute {
        search rules in index indexName query QueryRules("rule1", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should delete rule") {
      val res1 = client.execute {
        save rule generateRule("rule1") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = client.execute {
        save rule generateRule("rule2") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val d = client.execute {
        delete rule "rule1" from indexName
      }

      taskShouldBeCreatedAndWaitForIt(d, indexName)

      val s = client.execute {
        search rules in index indexName query QueryRules("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 1
      }
    }

    it("should clear rules") {
      val res1 = client.execute {
        save rule generateRule("rule1") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val res2 = client.execute {
        save rule generateRule("rule2") inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res2, indexName)

      val d = client.execute {
        clear rules AlgoliaDsl.of index indexName
      }

      taskShouldBeCreatedAndWaitForIt(d, indexName)

      val s = client.execute {
        search rules in index indexName query QueryRules("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 0
      }
    }

    it("should batch insert rules") {
      val rulesToInsert = Seq(
        generateRule("rule1"),
        generateRule("rule2")
      )

      val res1 = client.execute {
        save rules rulesToInsert inIndex indexName
      }

      taskShouldBeCreatedAndWaitForIt(res1, indexName)

      val s = client.execute {
        search rules in index indexName query QueryRules("", hitsPerPage = Some(10))
      }

      whenReady(s) { res =>
        res.hits should have size 2
      }
    }

  }

}
