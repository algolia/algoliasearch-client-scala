package algolia

import scala.concurrent.Future

trait Executable[QUERY, RESULT] {

  def apply(client: AlgoliaClient, query: QUERY): Future[RESULT]

}
