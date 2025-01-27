/** Composition API Composition API.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.composition

import algoliasearch.composition.MatchLevel._

/** Snippets that show the context around a matching search query.
  *
  * @param value
  *   Highlighted attribute value, including HTML tags.
  */
case class SnippetResultOption(
    value: String,
    matchLevel: MatchLevel
) extends SnippetResultTrait