package com.algolia.cts

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.time.{Millis, Seconds, Span}

class BaseTest extends AnyFlatSpec with ScalaFutures {
  implicit val defaultPatience =
    PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
}
