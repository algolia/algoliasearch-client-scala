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

sealed trait Mode

/** Search mode the index will use to query for results.
  */
object Mode {
  case object NeuralSearch extends Mode {
    override def toString = "neuralSearch"
  }
  case object KeywordSearch extends Mode {
    override def toString = "keywordSearch"
  }
  val values: Seq[Mode] = Seq(NeuralSearch, KeywordSearch)

  def withName(name: String): Mode = Mode.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown Mode value: $name"))
}

class ModeSerializer
    extends CustomSerializer[Mode](_ =>
      (
        {
          case JString(value) => Mode.withName(value)
          case JNull          => null
        },
        { case value: Mode =>
          JString(value.toString)
        }
      )
    )
