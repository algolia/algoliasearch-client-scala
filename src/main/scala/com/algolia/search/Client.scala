package com.algolia.search

import com.algolia.requester.{Functor, Requester}
import com.algolia.transport.Accept._
import com.algolia.transport._

import scala.util.Random

class Client[F[_]](config: Configuration)(
    implicit
    requester: Requester[F],
    functor: Functor[F],
) {
  private val transport = Transport(
    config.appID,
    config.apiKey,
    defaultHosts(config.appID),
    config.readTimeout,
    config.writeTimeout,
  )

  private def defaultHosts(appID: String): Seq[StatefulHost] = {
    Seq(
      StatefulHost(s"$appID-dsn.com.algolia.net", Read),
      StatefulHost(s"$appID.com.algolia.net", Write),
    ) ++ Random.shuffle(
      Seq(
        StatefulHost(s"$appID-1.algolianet.com", ReadWrite),
        StatefulHost(s"$appID-2.algolianet.com", ReadWrite),
        StatefulHost(s"$appID-3.algolianet.com", ReadWrite),
      ),
    )
  }

  def initIndex[T: Manifest](indexName: String): Index[T, F] =
    new Index[T, F](transport, indexName)
}

object Client {
  def apply[F[_]](applicationID: String, apiKey: String)(
      implicit
      requester: Requester[F],
      functor: Functor[F],
  ): Client[F] = new Client(Configuration(applicationID, apiKey))

  def apply[F[_]](configuration: Configuration)(
      implicit
      requester: Requester[F],
      functor: Functor[F],
  ): Client[F] = new Client(configuration)
}
