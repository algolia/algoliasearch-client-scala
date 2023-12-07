/** Recommend API The Recommend API lets you generate recommendations with several AI models. > **Note**: You should use
  * Algolia's [libraries and
  * tools](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) to interact with
  * the Recommend API. Using the HTTP endpoints directly is not covered by the
  * [SLA](https://www.algolia.com/policies/sla/).
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.recommend

import org.json4s._

/** Removes stop (common) words from the query before executing it. `removeStopWords` is used in conjunction with the
  * `queryLanguages` setting. _list_: language ISO codes for which stop words should be enabled. This list will override
  * any values that you may have set in `queryLanguages`. _true_: enables the stop words feature, ensuring that stop
  * words are removed from consideration in a search. The languages supported here are either [every
  * language](https://www.algolia.com/doc/guides/managing-results/optimize-search-results/handling-natural-languages-nlp/in-depth/supported-languages/)
  * (this is the default) or those set by `queryLanguages`. _false_: turns off the stop words feature, allowing stop
  * words to be taken into account in a search.
  */
sealed trait RemoveStopWords

object RemoveStopWords {

  case class SeqOfString(value: Seq[String]) extends RemoveStopWords
  case class BooleanValue(value: Boolean) extends RemoveStopWords

  def apply(value: Seq[String]): RemoveStopWords = {
    RemoveStopWords.SeqOfString(value)
  }
  def apply(value: Boolean): RemoveStopWords = {
    RemoveStopWords.BooleanValue(value)
  }
}

object RemoveStopWordsSerializer extends Serializer[RemoveStopWords] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), RemoveStopWords] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[RemoveStopWords] =>
      json match {
        case JArray(value) if value.forall(_.isInstanceOf[JArray]) => RemoveStopWords.SeqOfString(value.map(_.extract))
        case JBool(value)                                          => RemoveStopWords.BooleanValue(value)
        case _ => throw new MappingException("Can't convert " + json + " to RemoveStopWords")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: RemoveStopWords =>
    value match {
      case RemoveStopWords.SeqOfString(value)  => JArray(value.map(Extraction.decompose).toList)
      case RemoveStopWords.BooleanValue(value) => JBool(value)
    }
  }
}
