package com.algolia.cts

import com.algolia.cts.Utils.Record
import com.algolia.requester.{Functor, Requester}
import com.algolia.search.Client
import org.scalatest.flatspec.AnyFlatSpec

abstract class IndexingBase[F[_]](
    implicit
    requester: Requester[F],
    functor: Functor[F]
) extends AnyFlatSpec {

  lazy val client1 = Client[F]("", "")
  val index = client1.initIndex[Record]("scala")
  val res = index.saveObject(Record("one"))

  functor.map(res) {
    case Left(err)    => println(err)
    case Right(value) => println(value)
  }
}
