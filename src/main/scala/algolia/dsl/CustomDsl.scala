package algolia.dsl

import algolia.definitions.CustomRequestDefinition
import algolia.http.HttpVerb
import algolia.objects.{RequestEndpoint, RequestOptions}
import algolia.responses.Task
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait CustomDsl {

  implicit val formats: Formats

  case object custom {

    def request(
        verb: HttpVerb,
        path: Seq[String],
        requestEndpoint: RequestEndpoint,
        queryParameters: Option[Map[String, String]] = None,
        body: Option[String] = None,
        requestOptions: Option[RequestOptions]
    ): CustomRequestDefinition =
      CustomRequestDefinition(
        verb,
        path,
        requestEndpoint,
        queryParameters,
        body,
        requestOptions
      )
  }

  implicit object CustomRequestDefinitionExecutable
      extends Executable[CustomRequestDefinition, Task] {
    override def apply(client: AlgoliaClient, query: CustomRequestDefinition)(
        implicit executor: ExecutionContext
    ): Future[Task] = { client.request[Task](query.build()) }
  }
}
