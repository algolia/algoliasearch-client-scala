package algolia.definitions

import algolia.http.{HttpPayload, POST}
import algolia.inputs.InsightsEvent
import algolia.objects.RequestOptions
import org.json4s.Formats
import org.json4s.native.Serialization.write

case class InsightsEventDefinition(
    events: Iterable[InsightsEvent],
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = InsightsEventDefinition

  override def options(requestOptions: RequestOptions): InsightsEventDefinition =
    copy(requestOptions = Some(requestOptions))

  override private[algolia] def build(): HttpPayload = {
    val body = Map("events" -> events)

    HttpPayload(
      POST,
      Seq("1", "events"),
      body = Some(write(body)),
      isSearch = false,
      isInsights = true,
      requestOptions = requestOptions
    )
  }

}
