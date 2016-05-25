/*
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia.http


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, ExecutionException, Future}

class EnrichedFuture[A](underlying: Future[A]) {

  /** Project promised value into an either containing the value or any
    *  exception thrown retrieving it. Unwraps `cause` of any top-level
    *  ExecutionException */
  def either: Future[Either[Throwable, A]] = {
    implicit val ctx = EnrichedFuture.currentThreadContext

    underlying.map { res => Right(res) }.recover {
      case exc: ExecutionException => Left(exc.getCause)
      case throwable => Left(throwable)
    }
  }
  /** Create a left projection of a contained either */
  def left[B,C](implicit ev: Future[A] <:< Future[Either[B, C]],
                executor: ExecutionContext) =
    new FutureEither.LeftProjection(underlying)

  /** Create a right projection of a contained either */
  def right[B,C](implicit ev: Future[A] <:< Future[Either[B, C]],
                 executor: ExecutionContext) =
    new FutureEither.RightProjection(underlying)

  /** Project any resulting exception or result into a unified type X */
  def fold[X](fa: Throwable => X, fb: A => X)
             (implicit executor: ExecutionContext): Future[X] =
    for (eth <- either) yield eth.fold(fa, fb)

  def flatten[B]
  (implicit pv: Future[A] <:< Future[Future[B]],
   executor: ExecutionContext): Future[B] =
    (underlying: Future[Future[B]]).flatMap(identity)

  /** Facilitates projection over promised iterables */
  def values[B](implicit ev: Future[A] <:< Future[Iterable[B]],
                executor: ExecutionContext) =
    new FutureIterable.Values(underlying)

  /** Project promised value into an Option containing the value if retrived
    *  with no exception */
  def option: Future[Option[A]] = {
    implicit val ctx = EnrichedFuture.currentThreadContext
    either.map { _.right.toOption }
  }

  def apply() = Await.result(underlying, Duration.Inf)

  /** Some value if promise is complete, otherwise None */
  def completeOption =
    for (tried <- underlying.value) yield tried.get

  def print =
    "Future(%s)".format(either.completeOption.map {
      case Left(exc) => "!%s!".format(exc.getMessage)
      case Right(value) => value.toString
    }.getOrElse("-incomplete-"))
}

object EnrichedFuture {
  /** Execute on the current thread, for certain cpu-bound operations */
  private val currentThreadContext = new ExecutionContext {
    def execute(runnable: Runnable) {
      runnable.run()
    }
    def reportFailure(t: Throwable) {
      ExecutionContext.defaultReporter(t)
    }
  }
}
