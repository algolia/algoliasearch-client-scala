package com.algolia.search

import com.algolia.requester.Requester
import com.algolia.transport.Accept._
import com.algolia.transport._

import scala.concurrent.ExecutionContext
import scala.util.Random

class Client(
    config: Configuration
)(
    implicit
    requester: Requester,
    ex: ExecutionContext
) {
  private val transport = Transport(
    config.appID,
    config.apiKey,
    defaultHosts(config.appID),
    config.readTimeout,
    config.writeTimeout
  )

  private def defaultHosts(appID: String): Seq[StatefulHost] = {
    Seq(
      StatefulHost(s"$appID-dsn.algolia.net", Read),
      StatefulHost(s"$appID.algolia.net", Write)
    ) ++ Random.shuffle(
      Seq(
        StatefulHost(s"$appID-1.algolianet.com", ReadWrite),
        StatefulHost(s"$appID-2.algolianet.com", ReadWrite),
        StatefulHost(s"$appID-3.algolianet.com", ReadWrite)
      )
    )
  }

  def initIndex[T: Manifest](indexName: String): Index[T] =
    new Index[T](transport, indexName)
}

object Client {
  def apply(applicationID: String, apiKey: String)(
      implicit
      requester: Requester,
      ex: ExecutionContext
  ): Client = new Client(Configuration(applicationID, apiKey))

  def apply(configuration: Configuration)(
      implicit
      requester: Requester,
      ex: ExecutionContext
  ): Client = new Client(configuration)
}
