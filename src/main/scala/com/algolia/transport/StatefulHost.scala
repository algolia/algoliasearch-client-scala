package com.algolia.transport

import java.time.LocalDateTime
import Accept._

class StatefulHost private (
    host: String,
    accept: Accept = ReadWrite,
    isDown: Boolean = false,
    retryCount: Int = 0,
    updatedAt: LocalDateTime = LocalDateTime.now(),
)

object StatefulHost {
  def apply(host: String, accept: Accept) =
    new StatefulHost(host, accept)
}
