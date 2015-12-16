package algolia.example

import java.io.File

import algolia.AlgoliaClient
import algolia.responses.Indexing
import com.github.tototoshi.csv._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object config {
  val appId = ""
  val secret = ""
}

object CSVExample {
  import algolia.AlgoliaDsl._

  case class Reservation(rank: Int, medium: String, reservations: Int, title: String, author: String)
  object FileFormat extends DefaultCSVFormat {
    override val delimiter = ';'
  }

  def main(args: Array[String]) = {
    println("Start importing data")

    val client = new AlgoliaClient(config.appId, config.secret)
    val reader = CSVReader.open("example/csv-loader/src/main/resources/top1000libraryParis.utf8.csv")(FileFormat)

    val operations:Seq[Future[Indexing]] = reader.toStream()
      .map {
        case rank :: medium :: reservations :: title :: author :: nil => {
          println("Yeah or not")
          Reservation(rank.toInt, medium, reservations.toInt, title, author)
        }
        case _ => throw new Exception("can't parse")
      }
      .map { r: Reservation =>
        client execute {
          index into "scala-test" document r
        }
      }
/* This alternative is not working yet...
      .grouped(1000)
      .map { rs: Stream[Reservation] =>
        client execute {
          index into "scala-test" documents rs
        }
      }*/

    println("Number of operations : " + operations.length)

    val allInOne = Future.sequence(operations)

    allInOne.onComplete {
      case Success(_) => {
        println("Successfully insert")
        sys.exit(0)
      }
      case Failure(e) => {
        println("Error during import ", e)
        sys.exit(1)
      }
    }

    Await.result(allInOne, Duration.Inf)

    ()
  }
}
