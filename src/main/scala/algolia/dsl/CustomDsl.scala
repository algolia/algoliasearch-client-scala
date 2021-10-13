package algolia.dsl

import algolia.definitions.CustomRequestDefinition
import algolia.objects.CustomRequest
import algolia.{AlgoliaClient, Executable}
import org.json4s.{Formats, JObject}

import scala.concurrent.{ExecutionContext, Future}

trait CustomDsl {

  implicit val formats: Formats

  case object custom {

    def request(request: CustomRequest): CustomRequestDefinition =
      CustomRequestDefinition(request)
  }

  implicit object CustomRequestDefinitionExecutable
      extends Executable[CustomRequestDefinition, JObject] {
    override def apply(client: AlgoliaClient, query: CustomRequestDefinition)(
        implicit executor: ExecutionContext
    ): Future[JObject] = { client.request[JObject](query.build()) }
  }
}
