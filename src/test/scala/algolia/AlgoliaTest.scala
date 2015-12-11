package algolia

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

class AlgoliaTest extends FunSpec with Matchers with BeforeAndAfter with ScalaFutures with MockFactory {

  val applicationId = System.getenv("APPLICATION_ID")
  val apiKey = System.getenv("API_KEY")

  implicit val patience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(250, Millis))

}
