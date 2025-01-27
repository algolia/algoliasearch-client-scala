/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

/** Widgets returned from any rules that are applied to the current search.
  *
  * @param banners
  *   Banners defined in the Merchandising Studio for a given search.
  */
case class Widgets(
    banners: Option[Seq[Banner]] = scala.None
)