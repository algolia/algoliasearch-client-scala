package algolia.definitions

import algolia.http.{GET, HttpPayload}
import algolia.objects.{RecommendationsOptions, RequestOptions}
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class GetRecommendationDefinition(
    query: RecommendationsOptions,
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = GetRecommendationDefinition

  override def options(
      requestOptions: RequestOptions
  ): GetRecommendationDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes", "*", "recommendations"),
      body = Some(write(query)),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
