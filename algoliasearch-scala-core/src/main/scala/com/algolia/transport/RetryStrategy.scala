package com.algolia.transport

import com.algolia.transport.Call.{Read, Write}

import scala.concurrent.duration.Duration

case class RetryStrategy(
    var hosts: Seq[StatefulHost],
    readTimeout: Duration,
    writeTimeout: Duration
) {
  def getTryableHosts(kind: Call): Seq[Host] = {
    def computeTimeout(retryCount: Int): Duration = {
      val baseTimeout = kind match {
        case Read  => readTimeout
        case Write => writeTimeout
      }
      baseTimeout * (retryCount + 1)
    }

    hosts.filter(_.accept(kind)).map(h => Host(h.host, computeTimeout(h.retryCount)))
  }

  def decide(h: Host, code: Int, error: Error): Unit = {}
}
