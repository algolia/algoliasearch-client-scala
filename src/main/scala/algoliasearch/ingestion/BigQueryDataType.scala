/** Ingestion API API powering the Data Ingestion connectors of Algolia.
  *
  * The version of the OpenAPI document: 1.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import org.json4s._

sealed trait BigQueryDataType

/** BigQueryDataType enumeration
  */
object BigQueryDataType {
  case object Ga4 extends BigQueryDataType {
    override def toString = "ga4"
  }
  case object Ga360 extends BigQueryDataType {
    override def toString = "ga360"
  }
  val values: Seq[BigQueryDataType] = Seq(Ga4, Ga360)

  def withName(name: String): BigQueryDataType = BigQueryDataType.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown BigQueryDataType value: $name"))
}

class BigQueryDataTypeSerializer
    extends CustomSerializer[BigQueryDataType](_ =>
      (
        {
          case JString(value) => BigQueryDataType.withName(value)
          case JNull          => null
        },
        { case value: BigQueryDataType =>
          JString(value.toString)
        }
      )
    )