package com.algolia.transport

import scala.concurrent.duration.Duration

case class RetryStrategy(
    var hosts: Seq[StatefulHost],
    readTimeout: Duration,
    writeTimeout: Duration,
) {
  def getTryableHosts(kind: Call): Seq[Host] = {
    Seq.empty
  }

  def decide(h: Host, code: Int, error: Error): Unit = {}
}
