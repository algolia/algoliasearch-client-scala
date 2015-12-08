package algolia

import scala.concurrent.{ExecutionContext, Future}

private[algolia] trait Executable[QUERY, RESULT] {

  def apply(client: AlgoliaClient, query: QUERY)(implicit executor: ExecutionContext): Future[RESULT]

}
