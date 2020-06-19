package com.algolia.errors

case class AlgoliaError(val status: Int, val message: String)

object AlgoliaError {
  def isRetryable(l: AlgoliaError): Boolean = true

  val NoHostToTry: AlgoliaError = AlgoliaError(0, "no host to try")
}
