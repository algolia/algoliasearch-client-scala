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

package algolia

import java.time.{Instant, LocalDateTime, ZoneOffset, ZonedDateTime}
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

import algolia.definitions._
import algolia.dsl._
import algolia.objects._
import org.json4s.{CustomSerializer, Formats}
import org.json4s.JsonAST._
import org.json4s.JsonDSL._

import scala.util.matching.Regex

trait AlgoliaDsl
    extends Object //Just to have all trait DSL ordered `with`
    with KeyDefinitionDsl
    with AddDsl
    with ABTestDsl
    with BatchDsl
    with BrowseDsl
    with ClearDsl
    with CopyDsl
    with DeleteDsl
    with GetDsl
    with IndexingBatchDsl
    with IndexingDsl
    with IndexSettingsDsl
    with ListDsl
    with LogsDsl
    with MoveDsl
    with MultiQueriesDefinitionDsl
    with PartialUpdateObjectDsl
    with RulesDsl
    with SaveDsl
    with SearchDsl
    with StopDsl
    with SynonymsDsl
    with WaitForTaskDsl
    with TaskStatusDsl
    with AssignDsl
    with RemoveDsl
    with SendDsl
    with SetDsl
    with RestoreDsl

object AlgoliaDsl extends AlgoliaDsl {

  implicit val formats: Formats =
    org.json4s.DefaultFormats +
      new SearchableAttributesSerializer +
      new AttributesToIndexSerializer +
      new NumericAttributesToIndexSerializer +
      new RankingSerializer +
      new CustomRankingSerializer +
      new QueryTypeSerializer +
      new TypoToleranceSerializer +
      new AclSerializer +
      new QuerySynonymsSerializer +
      new AbstractSynonymSerializer +
      new DistinctSerializer +
      new RemoveStopWordsSerializer +
      new IgnorePluralsSerializer +
      new LocalDateTimeSerializer +
      new ZonedDateTimeSerializer

  val searchableAttributesUnordered: Regex = """^unordered\(([\w-]+)\)$""".r
  val searchableAttributesAttributes: Regex =
    """^([\w-]+,[\w-]+[,[\w-]+]*)$""".r
  val numericAttributesToIndexEqualOnly: Regex =
    """^equalOnly\(([\w-]+)\)$""".r
  val asc: Regex = """^asc\(([\w-]+)\)$""".r
  val desc: Regex = """^desc\(([\w-]+)\)$""".r

  sealed trait ForwardToReplicas

  sealed trait ClearExistingRules

  sealed trait ReplaceExistingSynonyms

  sealed trait Of

  sealed trait In

  sealed trait ABTests

  class AttributesToIndexSerializer
      extends CustomSerializer[AttributesToIndex](_ =>
        ({
          case JString(searchableAttributesUnordered(attr)) =>
            AttributesToIndex.unordered(attr)
          case JString(searchableAttributesAttributes(attrs)) =>
            AttributesToIndex.attributes(attrs.split(","): _*)
          case JString(attr) => AttributesToIndex.attribute(attr)
        }, {
          case AttributesToIndex.unordered(attr) =>
            JString(s"unordered($attr)")
          case AttributesToIndex.attribute(attr) => JString(attr)
          case AttributesToIndex.attributes(attributes @ _ *) =>
            JString(attributes.mkString(","))
        }))

  class SearchableAttributesSerializer
      extends CustomSerializer[SearchableAttributes](_ =>
        ({
          case JString(searchableAttributesUnordered(attr)) =>
            SearchableAttributes.unordered(attr)
          case JString(searchableAttributesAttributes(attrs)) =>
            SearchableAttributes.attributes(attrs.split(","): _*)
          case JString(attr) => SearchableAttributes.attribute(attr)
        }, {
          case SearchableAttributes.unordered(attr) =>
            JString(s"unordered($attr)")
          case SearchableAttributes.attribute(attr) => JString(attr)
          case SearchableAttributes.attributes(attributes @ _ *) =>
            JString(attributes.mkString(","))
        }))

  class NumericAttributesToIndexSerializer
      extends CustomSerializer[NumericAttributesToIndex](_ =>
        ({
          case JString(numericAttributesToIndexEqualOnly(attr)) =>
            NumericAttributesToIndex.equalOnly(attr)
        }, {
          case NumericAttributesToIndex.equalOnly(attr) =>
            JString(s"equalOnly($attr)")
        }))

  class RankingSerializer
      extends CustomSerializer[Ranking](_ =>
        ({
          case JString(asc(attr)) => Ranking.asc(attr)
          case JString(desc(attr)) => Ranking.desc(attr)
          case JString("typo") => Ranking.typo
          case JString("geo") => Ranking.geo
          case JString("words") => Ranking.words
          case JString("proximity") => Ranking.proximity
          case JString("attribute") => Ranking.attribute
          case JString("exact") => Ranking.exact
          case JString("custom") => Ranking.custom
          case JString("filters") => Ranking.filters
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
          case Ranking.filters => JString("filters")
        }))

  class CustomRankingSerializer
      extends CustomSerializer[CustomRanking](_ =>
        ({
          case JString(asc(attr)) => CustomRanking.asc(attr)
          case JString(desc(attr)) => CustomRanking.desc(attr)
        }, {
          case CustomRanking.asc(attribute) => JString(s"asc($attribute)")
          case CustomRanking.desc(attribute) => JString(s"desc($attribute)")
        }))

  class QueryTypeSerializer
      extends CustomSerializer[QueryType](_ =>
        ({
          case JString("prefixAll") => QueryType.prefixAll
          case JString("prefixLast") => QueryType.prefixLast
          case JString("prefixNone") => QueryType.prefixNone
        }, {
          case QueryType.prefixAll => JString("prefixAll")
          case QueryType.prefixLast => JString("prefixLast")
          case QueryType.prefixNone => JString("prefixNone")
        }))

  class TypoToleranceSerializer
      extends CustomSerializer[TypoTolerance](_ =>
        ({
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

  class AclSerializer
      extends CustomSerializer[Acl](_ =>
        ({
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

  class QuerySynonymsSerializer
      extends CustomSerializer[QuerySynonyms](_ =>
        ({
          case JObject(_) => ???
        }, {
          case QuerySynonyms(query, synonymsTypes, page, hitsPerPage) =>
            var fields = Seq(JField("query", JString(query)))

            synonymsTypes.foreach(t => fields :+= JField("type", JString(t.mkString(","))))
            page.foreach(p => fields :+= JField("page", JInt(p)))
            hitsPerPage.foreach(p => fields :+= JField("hitsPerPage", JInt(p)))

            JObject(fields: _*)
        }))

  class AbstractSynonymSerializer
      extends CustomSerializer[AbstractSynonym](_ =>
        ({
          case x: JObject =>
            val objectID = (x \ "objectID").extract[String]
            val synonymType = (x \ "type").extract[String]

            synonymType match {
              case SynonymType.synonym.name =>
                Synonym.Synonym(objectID, (x \ "synonyms").extract[Seq[String]])
              case SynonymType.altCorrection1.name =>
                Synonym.AltCorrection1(objectID,
                                       (x \ "word").extract[String],
                                       (x \ "corrections").extract[Seq[String]])
              case SynonymType.altCorrection2.name =>
                Synonym.AltCorrection2(objectID,
                                       (x \ "word").extract[String],
                                       (x \ "corrections").extract[Seq[String]])
              case SynonymType.oneWaySynonym.name =>
                Synonym.OneWaySynonym(objectID,
                                      (x \ "input").extract[String],
                                      (x \ "synonyms").extract[Seq[String]])
              case SynonymType.placeholder.name =>
                Synonym.Placeholder(objectID,
                                    (x \ "placeholder").extract[String],
                                    (x \ "replacements").extract[Seq[String]])
            }
        }, {
          case Synonym.Synonym(oid, s) =>
            ("objectID" -> oid) ~ ("synonyms" -> s) ~ ("type" -> "synonym")
          case Synonym.AltCorrection1(oid, w, c) =>
            ("objectID" -> oid) ~ ("word" -> w) ~ ("corrections" -> c) ~ ("type" -> "altCorrection1")
          case Synonym.AltCorrection2(oid, w, c) =>
            ("objectID" -> oid) ~ ("word" -> w) ~ ("corrections" -> c) ~ ("type" -> "altCorrection2")
          case Synonym.OneWaySynonym(oid, i, s) =>
            ("objectID" -> oid) ~ ("input" -> i) ~ ("synonyms" -> s) ~ ("type" -> "oneWaySynonym")
          case Synonym.Placeholder(oid, p, r) =>
            ("objectID" -> oid) ~ ("placeholder" -> p) ~ ("replacements" -> r) ~ ("type" -> "placeholder")
        }))

  class DistinctSerializer
      extends CustomSerializer[Distinct](_ =>
        ({
          case JBool(true) => Distinct.`true`
          case JBool(false) => Distinct.`false`
          case JInt(i) => Distinct.int(i.toInt)
        }, {
          case Distinct.`true` => JBool(true)
          case Distinct.`false` => JBool(false)
          case Distinct.int(i) => JInt(i)
        }))

  class RemoveStopWordsSerializer
      extends CustomSerializer[RemoveStopWords](_ =>
        ({
          case JBool(true) => RemoveStopWords.`true`
          case JBool(false) => RemoveStopWords.`false`
          case JString(list) => RemoveStopWords.list(list.split(","))
        }, {
          case RemoveStopWords.`true` => JBool(true)
          case RemoveStopWords.`false` => JBool(false)
          case RemoveStopWords.list(i) => JString(i.mkString(","))
        }))

  class IgnorePluralsSerializer
      extends CustomSerializer[IgnorePlurals](_ =>
        ({
          case JBool(true) => IgnorePlurals.`true`
          case JBool(false) => IgnorePlurals.`false`
          case JString(list) => IgnorePlurals.list(list.split(","))
        }, {
          case IgnorePlurals.`true` => JBool(true)
          case IgnorePlurals.`false` => JBool(false)
          case IgnorePlurals.list(i) => JString(i.mkString(","))
        }))

  val iso8601WithNsFormatter = new DateTimeFormatterBuilder()
    .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    .appendPattern("[.SSSSSSSSS][.SSSSSS][.SSS]")
    .appendOffset("+HH:mm", "Z")
    .toFormatter()

  class LocalDateTimeSerializer
      extends CustomSerializer[LocalDateTime](_ =>
        ({
          case JString(s) => LocalDateTime.parse(s, iso8601WithNsFormatter)
        }, {
          case ts: LocalDateTime =>
            JString(ts.atOffset(ZoneOffset.UTC).format(iso8601WithNsFormatter))
        }))

  class ZonedDateTimeSerializer
      extends CustomSerializer[ZonedDateTime](_ =>
        ({
          case JInt(l) =>
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(l.longValue()), ZoneOffset.UTC)
        }, {
          case ts: ZonedDateTime => JInt(ts.toEpochSecond())
        }))

  case object forwardToSlaves extends ForwardToReplicas

  case object forwardToReplicas extends ForwardToReplicas

  case object replaceExistingSynonyms extends ReplaceExistingSynonyms

  case object clearExistingRules extends ClearExistingRules

  case object of extends Of

  case object in extends In

  case object abTests extends ABTests

}
