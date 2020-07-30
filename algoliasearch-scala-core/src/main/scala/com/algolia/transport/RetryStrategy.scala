package com.algolia.transport

import com.algolia.errors.AlgoliaError
import com.algolia.transport.Call.{Read, Write}
import com.algolia.transport.Outcome.Success

import scala.concurrent.duration.Duration

case class RetryStrategy(
    var hosts: Seq[StatefulHost],
    readTimeout: Duration,
    writeTimeout: Duration
) {

  def getTryableHosts(kind: Call): Seq[Host] = {
    hosts
      .filter(_.accept(kind))
      .map(h => Host(h.host, computeTimeout(kind, h.retryCount)))
  }

  private def computeTimeout(kind: Call, retryCount: Int): Duration = {
    val baseTimeout = kind match {
      case Read  => readTimeout
      case Write => writeTimeout
    }
    baseTimeout * (retryCount + 1)
  }

  def decide(h: Host, code: Int, error: Option[AlgoliaError]): Outcome = {
    Success
  }

  private def isZero(code: Int): Boolean = code == 0
  private def is2xx(code: Int): Boolean = 200 <= code && code < 300
  private def is4xx(code: Int): Boolean = 400 <= code && code < 400
}
