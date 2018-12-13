package algolia.dsl

import algolia.{AlgoliaClient, Executable}
import algolia.definitions.InsightsEventDefinition
import algolia.inputs._
import algolia.responses.InsightsEventResponse
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait SendDsl {
  implicit val formats: Formats

  case object send {

    def event(e: InsightsEvent): InsightsEventDefinition = InsightsEventDefinition(Seq(e))
    def events(e: Iterable[InsightsEvent]): InsightsEventDefinition = InsightsEventDefinition(e)

  }

  implicit object SendInsightEventExecutable
      extends Executable[InsightsEventDefinition, InsightsEventResponse] {
    override def apply(client: AlgoliaClient, query: InsightsEventDefinition)(
        implicit executor: ExecutionContext): Future[InsightsEventResponse] = {
      client.request[InsightsEventResponse](query.build())
    }
  }
}
