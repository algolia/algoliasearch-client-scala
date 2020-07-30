package com.algolia.errors

case class AlgoliaError(status: Int, message: String)

object AlgoliaError {
  val NoHostToTry: AlgoliaError = AlgoliaError(0, "no host to try")
}
