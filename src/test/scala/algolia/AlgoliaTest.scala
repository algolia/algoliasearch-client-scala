package algolia

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

trait AlgoliaTest extends FunSpec with BeforeAndAfter with Matchers with MockFactory with ScalaFutures {

  val applicationId = "4NB2RDC3KU" //System.getenv("ALGOLIA_APP_ID")
  val apiKey = "96af8d98d8896d80754ec3cd8f68c356" //System.getenv("ALGOLIA_API_KEY")

  implicit val defaultPatience =
    PatienceConfig(timeout = Span(2, Seconds), interval = Span(15, Millis))

}
