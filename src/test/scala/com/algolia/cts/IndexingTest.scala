package com.algolia.cts

import com.algolia.search.Responses._
import com.algolia.search.{Client, ObjectID}
import org.scalatest.flatspec.AnyFlatSpec

case class Record(objectID: String) extends ObjectID

class IndexingTest extends AnyFlatSpec {

  val appId: String = sys.env.getOrElse("ALGOLIA_APPLICATION_ID_ANTHONY", "")
  val apiKey: String = sys.env.getOrElse("ALGOLIA_ADMIN_KEY_ANTHONY", "")
  val indexName: String = "scala"

  import com.algolia.implicits.ApacheAsync._

  val client = Client(appId, apiKey)
  val index = client.initIndex[Record](indexName)

  val res: index.Response[SaveObjectRes] = index.saveObject(Record("one"))

  res.map {
    case Left(err)    => println(err)
    case Right(value) => println(value)
  }

}
