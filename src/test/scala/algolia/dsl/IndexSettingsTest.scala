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
import algolia.AlgoliaTest
import algolia.http.{GET, HttpPayload, PUT}
import algolia.objects._
import algolia.responses._
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization.writePretty

class IndexSettingsTest extends AlgoliaTest {

  implicit val formats =
    org.json4s.DefaultFormats +
      new AttributesToIndexSerializer +
      new NumericAttributesToIndexSerializer +
      new RankingSerializer +
      new CustomRankingSerializer +
      new QueryTypeSerializer +
      new TypoToleranceSerializer

  describe("get settings") {

    it("should get settings") {
      settings of "index"
    }

    it("should call API") {
      val payload = HttpPayload(GET, Seq("1", "indexes", "test", "settings"), isSearch = false)
      (settings of "test").build() should be(payload)
    }

  }

  describe("change settings") {

    it("should get settings") {
      changeSettings of "index" `with` IndexSettings()
    }

    it("should call API") {
      val payload = HttpPayload(PUT, Seq("1", "indexes", "test", "settings"), body = Some("{}"), isSearch = false)
      (changeSettings of "test" `with` IndexSettings()).build() should be(payload)
    }

  }

  describe("IndexSettings serialization/deserialization") {

    val json =
      """{
        |  "attributesToIndex":[
        |    "att1",
        |    "att2,att3",
        |    "unordered(att4)"
        |  ],
        |  "numericAttributesToIndex":[
        |    "equalOnly(att5)"
        |  ],
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
        |  "synonyms":[
        |    [
        |      "black",
        |      "dark"
        |    ],
        |    [
        |      "small",
        |      "little",
        |      "mini"
        |    ]
        |  ],
        |  "placeholders":{
        |    "<streetnumber>":[
        |      "1",
        |      "2",
        |      "3",
        |      "4",
        |      "5"
        |    ]
        |  },
        |  "altCorrections":[
        |    {
        |      "word":"foot",
        |      "correction":"feet",
        |      "nbTypos":1
        |    }
        |  ],
        |  "typoTolerance":"strict"
        |}""".stripMargin

    it("should deserialize json") {
      inside(parse(json).extract[IndexSettings]) { case i: IndexSettings =>
        i.attributesToIndex should be(Some(Seq(
          AttributesToIndex.attribute("att1"),
          AttributesToIndex.attributes("att2", "att3"),
          AttributesToIndex.unordered("att4"))
        ))
        i.numericAttributesToIndex should be(Some(Seq(
          NumericAttributesToIndex.equalOnly("att5")
        )))
        i.ranking should be(Some(Seq(Ranking.typo,
          Ranking.geo,
          Ranking.words,
          Ranking.proximity,
          Ranking.attribute,
          Ranking.exact,
          Ranking.custom,
          Ranking.asc("att6"),
          Ranking.desc("att7")
        )))
        i.customRanking should be(Some(Seq(
          CustomRanking.asc("att8"),
          CustomRanking.desc("att9")
        )))
        i.synonyms should be(Some(Seq(Seq("black", "dark"), Seq("small", "little", "mini"))))
        i.placeholders should be(Some(Map("<streetnumber>" -> Seq("1", "2", "3", "4", "5"))))
        i.altCorrections should be(Some(Seq(AltCorrection("foot", "feet", 1))))
        i.typoTolerance should be(Some(TypoTolerance.strict))
      }

    }

    it("should serialize json") {
      val i = IndexSettings(
        attributesToIndex = Some(Seq(AttributesToIndex.attribute("att1"), AttributesToIndex.attributes("att2", "att3"), AttributesToIndex.unordered("att4"))),
        numericAttributesToIndex = Some(Seq(NumericAttributesToIndex.equalOnly("att5"))),
        ranking = Some(Seq(
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
        customRanking = Some(Seq(
          CustomRanking.asc("att8"),
          CustomRanking.desc("att9")
        )),
        synonyms = Some(Seq(Seq("black", "dark"), Seq("small", "little", "mini"))),
        placeholders = Some(Map("<streetnumber>" -> Seq("1", "2", "3", "4", "5"))),
        altCorrections = Some(Seq(AltCorrection("foot", "feet", 1))),
        typoTolerance = Some(TypoTolerance.strict)
      )

      writePretty(i) should be(json)
    }

  }

}
