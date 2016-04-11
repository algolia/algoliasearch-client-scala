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

package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.http._
import algolia.objects.Synonym.Placeholder
import algolia.objects.{QuerySynonyms, SynonymType}
import algolia.{AlgoliaDsl, AlgoliaTest}

class SynonymsTest extends AlgoliaTest {

  describe("synonyms") {

    describe("get by id") {

      it("should get synonym by id") {
        get synonym "syn_id" from "toto"
      }

      it("should call API") {
        (get synonym "syn_id" from "toto").build() should be(
          HttpPayload(
            GET,
            Seq("1", "indexes", "toto", "synonyms", "syn_id"),
            isSearch = true
          )
        )
      }

    }

    describe("delete by id") {

      it("should get synonym by id") {
        delete synonym "syn_id" from "toto" and forwardToSlaves
      }

      it("should call API") {
        (delete synonym "syn_id" from "toto" and forwardToSlaves).build() should be(
          HttpPayload(
            DELETE,
            Seq("1", "indexes", "toto", "synonyms", "syn_id"),
            queryParameters = Some(Map("forwardToSlaves" -> "true")),
            isSearch = false
          )
        )
      }

    }

    describe("clear all") {

      it("should clear synonyms of an index") {
        //the `AlgoliaDsl.` is needed as the mixin trait Matchers already contains a `of` method
        clear synonyms AlgoliaDsl.of index "toto" and forwardToSlaves
      }

      it("should call API") {
        (clear synonyms AlgoliaDsl.of index "toto" and forwardToSlaves).build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "synonyms", "clear"),
            queryParameters = Some(Map("forwardToSlaves" -> "true")),
            isSearch = false
          )
        )
      }

    }

    describe("search") {

      it("should search synonyms of an index") {
        search synonyms in index "toto" query QuerySynonyms(query = "s", types = Some(Seq(SynonymType.placeholder, SynonymType.synonym)), page = Some(1), hitsPerPage = Some(1))
      }

      it("should call API") {
        (search synonyms in index "toto" query QuerySynonyms(query = "s", types = Some(Seq(SynonymType.placeholder, SynonymType.synonym)), page = Some(1), hitsPerPage = Some(1))).build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "synonyms", "search"),
            body = Some("""{"query":"s","type":"placeholder,synonym","page":1,"hitsPerPage":1}"""),
            isSearch = true
          )
        )
      }

    }

    describe("save") {

      it("should save synonyms of an index") {
        save synonym Placeholder("oid", "1", Seq("2", "3")) inIndex "toto" and forwardToSlaves
      }

      it("should call API") {
        (save synonym Placeholder("oid", "1", Seq("2", "3")) inIndex "toto" and forwardToSlaves).build() should be(
          HttpPayload(
            PUT,
            Seq("1", "indexes", "toto", "synonyms", "oid"),
            queryParameters = Some(Map("forwardToSlaves" -> "true")),
            body = Some("""{"objectID":"oid","placeholder":"1","replacements":["2","3"],"type":"placeholder"}"""),
            isSearch = false
          )
        )
      }

    }

    describe("batch") {

      it("should save batches synonyms of an index") {
        save synonyms Seq(Placeholder("oid", "1", Seq("2", "3"))) inIndex "toto" and forwardToSlaves and replaceExistingSynonyms
      }

      it("should call API") {
        (save synonyms Seq(Placeholder("oid", "1", Seq("2", "3"))) inIndex "toto" and forwardToSlaves and replaceExistingSynonyms).build() should be(
          HttpPayload(
            POST,
            Seq("1", "indexes", "toto", "synonyms", "batch"),
            queryParameters = Some(Map("forwardToSlaves" -> "true", "replaceExistingSynonyms" -> "true")),
            body = Some("""[{"objectID":"oid","placeholder":"1","replacements":["2","3"],"type":"placeholder"}]"""),
            isSearch = false
          )
        )
      }
    }

  }
}
