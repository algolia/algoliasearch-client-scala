package algolia.definitions
import algolia.http.HttpPayload
import algolia.objects.RequestOptions
import org.json4s.Formats

case class RenameIndexDefinition(
    currentIndexName: String,
    newIndexName: Option[String] = None,
    requestOptions: Option[RequestOptions] = None)(implicit val formats: Formats)
    extends Definition {

  override type T = RenameIndexDefinition

  override def options(requestOptions: RequestOptions): RenameIndexDefinition =
    copy(requestOptions = Some(requestOptions))

  def to(newIndexName: String): RenameIndexDefinition =
    copy(newIndexName = Some(newIndexName))

  override private[algolia] def build(): HttpPayload = {
    MoveIndexDefinition(
      currentIndexName,
      destination = newIndexName,
      requestOptions = requestOptions,
      scope = None
    ).build()
  }
}
