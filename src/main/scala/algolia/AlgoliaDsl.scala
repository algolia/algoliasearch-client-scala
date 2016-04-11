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

package algolia

import algolia.definitions._
import algolia.objects._
import org.json4s.CustomSerializer
import org.json4s.JsonAST._
import org.json4s.JsonDSL._

trait AlgoliaDsl
  extends Object //Just to have all trait DSL ordered `with`
    with ApiKeyDefinitionDsl
    with BatchDefinitionDsl
    with BrowseIndexDsl
    with ClearDsl
    with CopyIndexDsl
    with DeleteDsl
    with GetDsl
    with IndexingBatchDsl
    with IndexingDsl
    with IndexSettingsDsl
    with ListIndexesDsl
    with LogsDsl
    with MoveIndexDsl
    with MultiQueriesDefinitionDsl
    with PartialUpdateObjectDsl
    with SearchDsl
    with SynonymsDsl
    with WaitForTaskDsl

object AlgoliaDsl extends AlgoliaDsl {

  implicit val formats =
    org.json4s.DefaultFormats +
      new AttributesToIndexSerializer +
      new NumericAttributesToIndexSerializer +
      new RankingSerializer +
      new CustomRankingSerializer +
      new QueryTypeSerializer +
      new TypoToleranceSerializer +
      new AclSerializer +
      new QuerySynonymsSerializer +
      new AbstractSynonymSerializer

  val attributesToIndexUnordered = """^unordered\(([\w-]+)\)$""".r
  val attributesToIndexAttributes = """^([\w-]+,[\w-]+[,[\w-]+]*)$""".r
  val numericAttributesToIndexEqualOnly = """^equalOnly\(([\w-]+)\)$""".r
  val asc = """^asc\(([\w-]+)\)$""".r
  val desc = """^desc\(([\w-]+)\)$""".r

  sealed trait ForwardToSlave

  sealed trait ReplaceExistingSynonyms

  sealed trait Of

  sealed trait In

  class AttributesToIndexSerializer extends CustomSerializer[AttributesToIndex](format => ( {
    case JString(attributesToIndexUnordered(attr)) => AttributesToIndex.unordered(attr)
    case JString(attributesToIndexAttributes(attrs)) => AttributesToIndex.attributes(attrs.split(","): _*)
    case JString(attr) => AttributesToIndex.attribute(attr)
  }, {
    case AttributesToIndex.unordered(attr) => JString(s"unordered($attr)")
    case AttributesToIndex.attribute(attr) => JString(attr)
    case AttributesToIndex.attributes(attributes@_*) => JString(attributes.mkString(","))
  }))

  class NumericAttributesToIndexSerializer extends CustomSerializer[NumericAttributesToIndex](format => ( {
    case JString(numericAttributesToIndexEqualOnly(attr)) => NumericAttributesToIndex.equalOnly(attr)
  }, {
    case NumericAttributesToIndex.equalOnly(attr) => JString(s"equalOnly($attr)")
  }))

  class RankingSerializer extends CustomSerializer[Ranking](format => ( {
    case JString(asc(attr)) => Ranking.asc(attr)
    case JString(desc(attr)) => Ranking.desc(attr)
    case JString("typo") => Ranking.typo
    case JString("geo") => Ranking.geo
    case JString("words") => Ranking.words
    case JString("proximity") => Ranking.proximity
    case JString("attribute") => Ranking.attribute
    case JString("exact") => Ranking.exact
    case JString("custom") => Ranking.custom
  }, {
    case Ranking.asc(attribute) => JString(s"asc($attribute)")
    case Ranking.desc(attribute) => JString(s"desc($attribute)")
    case Ranking.typo => JString("typo")
    case Ranking.geo => JString("geo")
    case Ranking.words => JString("words")
    case Ranking.proximity => JString("proximity")
    case Ranking.attribute => JString("attribute")
    case Ranking.exact => JString("exact")
    case Ranking.custom => JString("custom")
  }))

  class CustomRankingSerializer extends CustomSerializer[CustomRanking](format => ( {
    case JString(asc(attr)) => CustomRanking.asc(attr)
    case JString(desc(attr)) => CustomRanking.desc(attr)
  }, {
    case CustomRanking.asc(attribute) => JString(s"asc($attribute)")
    case CustomRanking.desc(attribute) => JString(s"desc($attribute)")
  }))

  class QueryTypeSerializer extends CustomSerializer[QueryType](format => ( {
    case JString("prefixAll") => QueryType.prefixAll
    case JString("prefixLast") => QueryType.prefixLast
    case JString("prefixNone") => QueryType.prefixNone
  }, {
    case QueryType.prefixAll => JString("prefixAll")
    case QueryType.prefixLast => JString("prefixLast")
    case QueryType.prefixNone => JString("prefixNone")
  }))

  class TypoToleranceSerializer extends CustomSerializer[TypoTolerance](format => ( {
    case JString("true") => TypoTolerance.`true`
    case JString("false") => TypoTolerance.`false`
    case JString("min") => TypoTolerance.min
    case JString("strict") => TypoTolerance.strict
  }, {
    case TypoTolerance.`true` => JString("true")
    case TypoTolerance.`false` => JString("false")
    case TypoTolerance.min => JString("min")
    case TypoTolerance.strict => JString("strict")
  }))

  class AclSerializer extends CustomSerializer[Acl](format => ( {
    case JString("search") => Acl.search
    case JString("browse") => Acl.browse
    case JString("addObject") => Acl.addObject
    case JString("deleteObject") => Acl.deleteObject
    case JString("deleteIndex") => Acl.deleteIndex
    case JString("settings") => Acl.settings
    case JString("editSettings") => Acl.editSettings
    case JString("analytics") => Acl.analytics
    case JString("listIndexes") => Acl.listIndexes
  }, {
    case Acl.search => JString("search")
    case Acl.browse => JString("browse")
    case Acl.addObject => JString("addObject")
    case Acl.deleteObject => JString("deleteObject")
    case Acl.deleteIndex => JString("deleteIndex")
    case Acl.settings => JString("settings")
    case Acl.editSettings => JString("editSettings")
    case Acl.analytics => JString("analytics")
    case Acl.listIndexes => JString("listIndexes")
  }))

  class QuerySynonymsSerializer extends CustomSerializer[QuerySynonyms](format => ( {
    case JObject(_) => ???
  }, {
    case QuerySynonyms(query, synonymsTypes, page, hitsPerPage) =>
      var fields = Seq(JField("query", JString(query)))

      synonymsTypes.foreach(t => fields :+= JField("type", JString(t.mkString(","))))
      page.foreach(p => fields :+= JField("page", JInt(p)))
      hitsPerPage.foreach(p => fields :+= JField("hitsPerPage", JInt(p)))

      JObject(fields: _*)
  }))

  class AbstractSynonymSerializer extends CustomSerializer[AbstractSynonym](format => ( {
    case x: JObject =>
      val objectID = (x \ "objectID").extract[String]
      val synonymType = (x \ "type").extract[String]

      synonymType match {
        case SynonymType.synonym.name => Synonym.Synonym(objectID, (x \ "synonyms").extract[Seq[String]])
        case SynonymType.altCorrection1.name => Synonym.AltCorrection1(objectID, (x \ "word").extract[String], (x \ "corrections").extract[Seq[String]])
        case SynonymType.altCorrection2.name => Synonym.AltCorrection2(objectID, (x \ "word").extract[String], (x \ "corrections").extract[Seq[String]])
        case SynonymType.oneWaySynonym.name => Synonym.OneWaySynonym(objectID, (x \ "input").extract[String], (x \ "synonyms").extract[Seq[String]])
        case SynonymType.placeholder.name => Synonym.Placeholder(objectID, (x \ "placeholder").extract[String], (x \ "replacements").extract[Seq[String]])
      }
  }, {
    case Synonym.Synonym(oid, s) => ("objectID" -> oid) ~ ("synonyms" -> s) ~ ("type" -> "synonym")
    case Synonym.AltCorrection1(oid, w, c) => ("objectID" -> oid) ~ ("word" -> w) ~ ("corrections" -> c) ~ ("type" -> "altCorrection1")
    case Synonym.AltCorrection2(oid, w, c) => ("objectID" -> oid) ~ ("word" -> w) ~ ("corrections" -> c) ~ ("type" -> "altCorrection2")
    case Synonym.OneWaySynonym(oid, i, s) => ("objectID" -> oid) ~ ("input" -> i) ~ ("synonyms" -> s) ~ ("type" -> "oneWaySynonym")
    case Synonym.Placeholder(oid, p, r) => ("objectID" -> oid) ~ ("placeholder" -> p) ~ ("replacements" -> r) ~ ("type" -> "placeholder")
  }))

  case object forwardToSlaves extends ForwardToSlave

  case object replaceExistingSynonyms extends ReplaceExistingSynonyms

  case object of extends Of

  case object in extends In

}
