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
import algolia.http._
import algolia.objects._
import algolia.{AlgoliaDsl, AlgoliaTest}

class RulesTest extends AlgoliaTest {

  describe("rules") {

    describe("get by id") {

      it("should get rule by id") {
        get rule "rule_id" from "toto"
      }

      it("should call API") {
        (get rule "rule_id" from "toto").build() should be(
          HttpPayload(
            GET,
            Seq("1", "indexes", "toto", "rules", "rule_id"),
            isSearch = true,
            requestOptions = None
          )
        )
      }

    }

    describe("delete by id") {

      it("should get rule by id") {
        delete rule "rule_id" from "toto" and forwardToSlaves
        delete rule "rule_id" from "toto" and forwardToReplicas
      }

      it("should call API") {
        (delete rule "rule_id" from "toto" and forwardToReplicas)
          .build() should be(
          HttpPayload(
            DELETE,
            Seq("1", "indexes", "toto", "rules", "rule_id"),
            queryParameters = Some(Map("forwardToReplicas" -> "true")),
            isSearch = false,
            requestOptions = None
          )
        )
      }

    }

    describe("clear all") {

      it("should clear rules of an index") {
        //the `AlgoliaDsl.` is needed as the mixin trait Matchers already contains a `of` method
        clear rules AlgoliaDsl.of index "toto" and forwardToSlaves
        clear rules AlgoliaDsl.of index "toto" and forwardToReplicas
      }

      it("should call API") {
        (clear rules AlgoliaDsl.of index "toto" and forwardToReplicas)
          .build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "rules", "clear"),
            queryParameters = Some(Map("forwardToReplicas" -> "true")),
            isSearch = false,
            requestOptions = None
          )
        )
      }

    }

    describe("search") {

      it("should search rules of an index") {
        search rules in index "toto" query QueryRules(query = "s",
                                                      page = Some(1),
                                                      hitsPerPage = Some(1))
      }

      it("should call API") {
        (search rules in index "toto" query QueryRules(query = "s",
                                                       page = Some(1),
                                                       hitsPerPage = Some(1))).build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "rules", "search"),
            body = Some("""{"query":"s","page":1,"hitsPerPage":1}"""),
            isSearch = true,
            requestOptions = None
          )
        )
      }

    }

    describe("save") {

      it("should save rules of an index") {
        save rule generateRule("rule1") inIndex "toto" and forwardToSlaves
        save rule generateRule("rule2") inIndex "toto" and forwardToReplicas
      }

      it("should call API") {
        (save rule generateRule("rule1") inIndex "toto" and forwardToReplicas)
          .build() should be(
          HttpPayload(
            PUT,
            Seq("1", "indexes", "toto", "rules", "rule1"),
            queryParameters = Some(Map("forwardToReplicas" -> "true")),
            body = Some(
              """{"objectID":"rule1","condition":{"pattern":"a","anchoring":"is"},"consequence":{"params":{"query":{"remove":["1"]}},"userData":{"a":"b"}},"description":"rule1"}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }

      it("should serialize correctly") {
        val rule = Rule(
          objectID = "rule1",
          condition = Condition(
            pattern = "a",
            anchoring = "is"
          ),
          consequence = Consequence(
            params = Some(Map("query" -> "1")),
            userData = Some(Map("a" -> "b"))
          )
        )

        (save rule rule inIndex "toto" and forwardToReplicas)
          .build() should be(
          HttpPayload(
            PUT,
            Seq("1", "indexes", "toto", "rules", "rule1"),
            queryParameters = Some(Map("forwardToReplicas" -> "true")),
            body = Some(
              """{"objectID":"rule1","condition":{"pattern":"a","anchoring":"is"},"consequence":{"params":{"query":"1"},"userData":{"a":"b"}}}"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }

    }

    describe("batch") {

      it("should save batches rules of an index") {
        save rules Seq(generateRule("rule1")) inIndex "toto" and forwardToSlaves and clearExistingRules
        save rules Seq(generateRule("rule2")) inIndex "toto" and forwardToReplicas and clearExistingRules

      }

      it("should call API") {
        (save rules Seq(generateRule("rule1")) inIndex "toto" and forwardToReplicas and clearExistingRules)
          .build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "rules", "batch"),
            queryParameters =
              Some(Map("forwardToReplicas" -> "true", "clearExistingRules" -> "true")),
            body = Some(
              """[{"objectID":"rule1","condition":{"pattern":"a","anchoring":"is"},"consequence":{"params":{"query":{"remove":["1"]}},"userData":{"a":"b"}},"description":"rule1"}]"""),
            isSearch = false,
            requestOptions = None
          )
        )
      }
    }

  }
}
