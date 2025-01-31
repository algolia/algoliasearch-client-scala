/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

import algoliasearch.composition.AroundRadiusAll._

import org.json4s._

/** Maximum radius for a search around a central location. This parameter works in combination with the `aroundLatLng`
  * and `aroundLatLngViaIP` parameters. By default, the search radius is determined automatically from the density of
  * hits around the central location. The search radius is small if there are many hits close to the central
  * coordinates.
  */
sealed trait AroundRadius

trait AroundRadiusTrait extends AroundRadius

object AroundRadius {

  case class IntValue(value: Int) extends AroundRadius

  def apply(value: Int): AroundRadius = {
    AroundRadius.IntValue(value)
  }

}

object AroundRadiusSerializer extends Serializer[AroundRadius] {
  override def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), AroundRadius] = {

    case (TypeInfo(clazz, _), json) if clazz == classOf[AroundRadius] =>
      json match {
        case JInt(value)    => AroundRadius.IntValue(value.toInt)
        case value: JString => Extraction.extract[AroundRadiusAll](value)
        case _              => throw new MappingException("Can't convert " + json + " to AroundRadius")
      }
  }

  override def serialize(implicit format: Formats): PartialFunction[Any, JValue] = { case value: AroundRadius =>
    value match {
      case AroundRadius.IntValue(value) => JInt(value)
      case value: AroundRadiusAll       => JString(value.toString)
    }
  }
}
