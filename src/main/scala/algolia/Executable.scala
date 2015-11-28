package algolia

import scala.concurrent.Future

private[algolia] trait Executable[QUERY, RESULT] {

  def apply(client: AlgoliaClient, query: QUERY): Future[RESULT]

}
