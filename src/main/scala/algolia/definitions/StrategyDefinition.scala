package algolia.definitions

import algolia.http.{GET, HttpPayload, POST}
import algolia.objects.Strategy
import algolia.objects.RequestOptions
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class GetPersonalizationStrategyDefinition(requestOptions: Option[RequestOptions] = None)(
    implicit val formats: Formats)
    extends Definition {

  override type T = GetPersonalizationStrategyDefinition

  override def options(requestOptions: RequestOptions): GetPersonalizationStrategyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      GET,
      Seq("1", "recommendation", "personalization", "strategy"),
      isSearch = true,
      requestOptions = requestOptions
    )
  }

}

case class SetPersonalizationStrategyDefinition(
    s: Strategy,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = SetPersonalizationStrategyDefinition

  override def options(requestOptions: RequestOptions): SetPersonalizationStrategyDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    HttpPayload(
      POST,
      Seq("1", "recommendation", "personalization", "strategy"),
      body = Some(write(s)),
      isSearch = false,
      requestOptions = requestOptions
    )
  }

}
