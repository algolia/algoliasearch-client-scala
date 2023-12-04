/** Query Suggestions API The Query Suggestions API lets you manage Algolia's Query Suggestions configurations. Query
  * Suggestions add new indices with popular search queries, external suggestions, or facet values to your Algolia
  * application. In your user interface, you can query the Query Suggestions indices like regular indices and add
  * [suggested searches](https://www.algolia.com/doc/guides/building-search-ui/ui-and-ux-patterns/query-suggestions/js/)
  * to guide users and speed up their search.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.querysuggestions

import org.json4s.{Extraction, Formats, JObject, JValue, Serializer, TypeInfo}

/** Error.
  */
case class ErrorBase(
    message: Option[String] = scala.None,
    additionalProperties: Map[String, JValue] = Map.empty
)

class ErrorBaseSerializer extends Serializer[ErrorBase] {

  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), ErrorBase] = {
    case (TypeInfo(clazz, _), json) if clazz == classOf[ErrorBase] =>
      json match {
        case jobject: JObject =>
          val formats = format - this
          val mf = manifest[ErrorBase]
          val obj = Extraction.extract[ErrorBase](jobject)(formats, mf)
          val properties = jobject.obj.toMap - "message"
          obj.copy(additionalProperties = properties)
        case _ => throw new IllegalArgumentException(s"Can't deserialize $json as ErrorBase")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: ErrorBase =>
    val formats = format - this // remove current serializer from formats to avoid stackoverflow
    Extraction.decompose(value.copy(additionalProperties = Map.empty))(formats) merge Extraction.decompose(
      value.additionalProperties
    )(formats)
  }
}
