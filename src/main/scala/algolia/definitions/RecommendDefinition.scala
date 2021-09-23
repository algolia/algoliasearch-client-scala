package algolia.definitions

import algolia.http.{GET, HttpPayload}
import algolia.objects.RequestOptions
import org.json4s.Formats

case class GetRecommendDefinition(
    requestOptions: Option[RequestOptions] = None
)(implicit val formats: Formats)
    extends Definition {

  override type T = GetRecommendDefinition

  override def options(
      requestOptions: RequestOptions
  ): GetRecommendDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "indexes", "*", "recommendations"),
      isSearch = true,
      requestOptions = requestOptions
    )
  }
}
