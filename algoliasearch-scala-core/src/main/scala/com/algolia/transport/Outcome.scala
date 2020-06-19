package com.algolia.transport

import com.algolia.errors.AlgoliaError

sealed trait Outcome

object Outcome {
  case object Success extends Outcome
  case object Retry extends Outcome
  case object Failure extends Outcome

  def apply[U](e: Either[AlgoliaError, U]): Outcome = {
    e match {
      case Left(_)  => Failure
      case Right(_) => Success
    }
  }
}
