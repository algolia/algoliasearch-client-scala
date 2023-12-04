/** Search API Use the Search REST API to manage your data (indices and records), implement search, and improve
  * relevance (with Rules, synonyms, and language dictionaries). Although Algolia provides a REST API, you should use
  * the official open source API [clients, libraries, and
  * tools](https://www.algolia.com/doc/guides/getting-started/how-algolia-works/in-depth/ecosystem/) instead. There's no
  * [SLA](https://www.algolia.com/policies/sla/) if you use the REST API directly.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.search

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
