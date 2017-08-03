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
import algolia.http.{GET, HttpPayload, PUT}
import algolia.objects._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.writePretty

class IndexSettingsTest extends AlgoliaTest {

  describe("get settings") {

    it("should get settings") {
      settings of "index"
    }

    it("should call API") {
      val payload = HttpPayload(
        GET,
        Seq("1", "indexes", "test", "settings"),
        Some(Map("getVersion" -> "2")),
        isSearch = true,
        requestOptions = None
      )
      (settings of "test").build() should be(payload)
    }

  }

  describe("change settings") {

    it("should get settings") {
      changeSettings of "index" `with` IndexSettings() and forwardToSlaves
      changeSettings of "index" `with` IndexSettings() and forwardToReplicas
    }

    it("should call API") {
      val payload = HttpPayload(
        PUT,
        Seq("1", "indexes", "test", "settings"),
        queryParameters = Some(Map("forwardToReplicas" -> "true")),
        body = Some("{}"),
        isSearch = false,
        requestOptions = None
      )

      (changeSettings of "test" `with` IndexSettings() and forwardToReplicas).build() should be(
        payload)
    }

  }

  describe("IndexSettings serialization/deserialization") {

    val json =
      """{
        |  "distinct":1,
        |  "attributesToIndex":[
        |    "att1",
        |    "att2,att3",
        |    "unordered(att4)"
        |  ],
        |  "searchableAttributes":[
        |    "att1",
        |    "att2,att3",
        |    "unordered(att4)"
        |  ],
        |  "numericAttributesToIndex":[
        |    "equalOnly(att5)"
        |  ],
        |  "removeStopWords":"fr,en",
        |  "ranking":[
        |    "typo",
        |    "geo",
        |    "words",
        |    "proximity",
        |    "attribute",
        |    "exact",
        |    "custom",
        |    "asc(att6)",
        |    "desc(att7)"
        |  ],
        |  "customRanking":[
        |    "asc(att8)",
        |    "desc(att9)"
        |  ],
        |  "typoTolerance":"strict",
        |  "ignorePlurals":"fr,en"
        |}""".stripMargin

    it("should deserialize json") {
      inside(parse(json).extract[IndexSettings]) {
        case i: IndexSettings =>
          i.attributesToIndex should be(
            Some(
              Seq(AttributesToIndex.attribute("att1"),
                  AttributesToIndex.attributes("att2", "att3"),
                  AttributesToIndex.unordered("att4"))))
          i.searchableAttributes should be(
            Some(
              Seq(SearchableAttributes.attribute("att1"),
                  SearchableAttributes.attributes("att2", "att3"),
                  SearchableAttributes.unordered("att4"))))
          i.numericAttributesToIndex should be(
            Some(
              Seq(
                NumericAttributesToIndex.equalOnly("att5")
              )))
          i.ranking should be(
            Some(
              Seq(Ranking.typo,
                  Ranking.geo,
                  Ranking.words,
                  Ranking.proximity,
                  Ranking.attribute,
                  Ranking.exact,
                  Ranking.custom,
                  Ranking.asc("att6"),
                  Ranking.desc("att7"))))
          i.customRanking should be(
            Some(
              Seq(
                CustomRanking.asc("att8"),
                CustomRanking.desc("att9")
              )))
          i.typoTolerance should be(Some(TypoTolerance.strict))
          i.distinct should be(Some(Distinct.int(1)))
          i.removeStopWords should be(Some(RemoveStopWords.list(Seq("fr", "en"))))
          i.ignorePlurals should be(Some(IgnorePlurals.list(Seq("fr", "en"))))
      }

    }

    it("should serialize json") {
      val i = IndexSettings(
        attributesToIndex = Some(
          Seq(AttributesToIndex.attribute("att1"),
              AttributesToIndex.attributes("att2", "att3"),
              AttributesToIndex.unordered("att4"))),
        searchableAttributes = Some(
          Seq(SearchableAttributes.attribute("att1"),
              SearchableAttributes.attributes("att2", "att3"),
              SearchableAttributes.unordered("att4"))),
        numericAttributesToIndex = Some(Seq(NumericAttributesToIndex.equalOnly("att5"))),
        ranking = Some(
          Seq(
            Ranking.typo,
            Ranking.geo,
            Ranking.words,
            Ranking.proximity,
            Ranking.attribute,
            Ranking.exact,
            Ranking.custom,
            Ranking.asc("att6"),
            Ranking.desc("att7")
          )),
        customRanking = Some(
          Seq(
            CustomRanking.asc("att8"),
            CustomRanking.desc("att9")
          )),
        ignorePlurals = Some(IgnorePlurals.list(Seq("fr", "en"))),
        typoTolerance = Some(TypoTolerance.strict),
        distinct = Some(Distinct.int(1)),
        removeStopWords = Some(RemoveStopWords.list(Seq("fr", "en")))
      )

      writePretty(i) should be(json)
    }

  }

}
