/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

import org.json4s._

sealed trait MatchLevel

/** Whether the whole query string matches or only a part.
  */
object MatchLevel {
  case object None extends MatchLevel {
    override def toString = "none"
  }
  case object Partial extends MatchLevel {
    override def toString = "partial"
  }
  case object Full extends MatchLevel {
    override def toString = "full"
  }
  val values: Seq[MatchLevel] = Seq(None, Partial, Full)

  def withName(name: String): MatchLevel = MatchLevel.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown MatchLevel value: $name"))
}

class MatchLevelSerializer
    extends CustomSerializer[MatchLevel](_ =>
      (
        {
          case JString(value) => MatchLevel.withName(value)
          case JNull          => null
        },
        { case value: MatchLevel =>
          JString(value.toString)
        }
      )
    )
