package algolia

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

trait AlgoliaTest extends FunSpec with BeforeAndAfter with Matchers with MockFactory with ScalaFutures {

  val applicationId = System.getenv("ALGOLIA_APP_ID")
  val apiKey = System.getenv("ALGOLIA_API_KEY")

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(15, Millis))

}
