package algolia.dsl

import algolia.{AlgoliaClient, Executable}
import algolia.definitions.{
  GetPersonalizationStrategyDefinition,
  SetPersonalizationStrategyDefinition
}
import algolia.inputs._
import algolia.objects.Strategy
import algolia.responses.SetStrategyResult
import org.json4s.Formats

import scala.concurrent.{ExecutionContext, Future}

trait SetDsl {
  implicit val formats: Formats

  case object set {

    def personalizationStrategy(s: Strategy): SetPersonalizationStrategyDefinition =
      SetPersonalizationStrategyDefinition(s)

  }

  implicit object SetPersonalizationStrategyExecutable
      extends Executable[SetPersonalizationStrategyDefinition, SetStrategyResult] {
    override def apply(client: AlgoliaClient, query: SetPersonalizationStrategyDefinition)(
        implicit executor: ExecutionContext): Future[SetStrategyResult] = {
      client.request[SetStrategyResult](query.build())
    }
  }

}
