package com.algolia.cts

import com.algolia.cts.UtilsBase.Record
import com.algolia.requester.Requester
import com.algolia.search.Client

class IndexingBase(implicit requester: Requester) extends BaseTest {

  implicit val ec = scala.concurrent.ExecutionContext.global
  val client1 = Client(UtilsBase.appId1, UtilsBase.adminKey1)
  val index = client1.initIndex[Record]("scala")
  val res = index.saveObject(Record("one"))

  println("BEGIN")

  whenReady(res) {
    case Left(err) =>
      println("KO")
      println(err)
    case Right(value) =>
      println("OK")
      println(value)
      assert(value.taskID == 0)
      assert(value.taskID == 1)
  }

  println("END")
}
