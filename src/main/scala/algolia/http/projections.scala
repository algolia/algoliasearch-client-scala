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

import scala.concurrent.{ExecutionContext, Future}

object FutureEither {

  class LeftProjection[+A, +B](underlying: Future[Either[A, B]])(
      implicit executor: ExecutionContext) {

    def flatMap[BB >: B, X](
        f: A => Future[Either[X, BB]]): Future[Either[X, BB]] =
      underlying.flatMap {
        _.fold(a => f(a), b => Future.successful(Right(b)))
      }

    def map[X](f: A => X): Future[Either[X, B]] =
      underlying.map {
        _.left.map(f)
      }

    def foreach[U](f: A => U) {
      underlying.foreach {
        _.left.foreach(f)
      }
    }
  }

  class RightProjection[+A, +B](underlying: Future[Either[A, B]])(
      implicit executor: ExecutionContext) {
    def flatMap[AA >: A, Y](
        f: B => Future[Either[AA, Y]]): Future[Either[AA, Y]] =
      underlying.flatMap { eth =>
        eth.fold(a => Future.successful(Left(a)), b => f(b))
      }

    def map[Y](f: B => Y): Future[Either[A, Y]] =
      underlying.map {
        _.right.map(f)
      }

    def foreach(f: B => Unit) {
      underlying.foreach {
        _.right.foreach(f)
      }
    }

    def values[A1 >: A, C](
        implicit ev: RightProjection[A, B] <:< RightProjection[
            A1, Iterable[C]]) =
      new FutureRightIterable.Values(underlying, this)
  }
}

object FutureIterable {

  class Flatten[A](val underlying: Future[Iterable[A]])(
      implicit executor: ExecutionContext) {

    def flatMap[Iter[B] <: Iterable[B], B](f: A => Future[Iter[B]]) =
      underlying.flatMap { iter =>
        Future.sequence(iter.map(f)).map {
          _.flatten
        }
      }

    def map[Iter[B] <: Iterable[B], B](f: A => Iter[B]): Future[Iterable[B]] =
      underlying.map {
        _.map(f)
      }.map {
        _.flatten
      }

    def foreach(f: A => Unit) {
      underlying.foreach {
        _.foreach(f)
      }
    }

    def filter(p: A => Boolean) = withFilter(p)

    def withFilter(p: A => Boolean) =
      new Flatten(
          underlying.map {
        _.filter(p)
      })
  }

  class Values[A](underlying: Future[Iterable[A]])(
      implicit executor: ExecutionContext) {
    def flatMap[B](f: A => Future[B]) =
      underlying.flatMap { iter =>
        Future.sequence(iter.map(f))
      }

    def map[B](f: A => B): Future[Iterable[B]] =
      underlying.map {
        _.map(f)
      }

    def foreach(f: A => Unit) {
      underlying.foreach {
        _.foreach(f)
      }
    }

    def filter(p: A => Boolean) = withFilter(p)

    def withFilter(p: A => Boolean) =
      new Values(
          underlying.map {
        _.filter(p)
      })

    def flatten = new Flatten(underlying)
  }
}

object FutureRightIterable {

  import FutureEither.RightProjection

  type RightIter[E, A] = RightProjection[E, Iterable[A]]

  private def flatRight[L, R](eithers: Iterable[Either[L, R]]) = {
    val start: Either[L, Seq[R]] = Right(Seq.empty)
    (start /: eithers) { (a, e) =>
      for {
        seq <- a.right
        cur <- e.right
      } yield (seq :+ cur)
    }
  }

  class Flatten[E, A](parent: Future[_], underlying: RightIter[E, A])(
      implicit executor: ExecutionContext) {
    def flatMap[Iter[B] <: Iterable[B], B](
        f: A => Future[Either[E, Iter[B]]]) =
      underlying.flatMap { iter =>
        Future.sequence(iter.map(f)).map { eths =>
          flatRight(eths).right.map {
            _.flatten
          }
        }
      }

    def map[Iter[B] <: Iterable[B], B](f: A => Iter[B]) =
      underlying.flatMap { iter =>
        Future.successful(Right(iter.map(f).flatten))
      }

    def foreach(f: A => Unit) {
      underlying.foreach {
        _.foreach(f)
      }
    }

    def filter(p: A => Boolean) = withFilter(p)

    def withFilter(p: A => Boolean) =
      new Values(parent, underlying.map {
        _.filter(p)
      }.right)
  }

  class Values[E, A](parent: Future[_], underlying: RightIter[E, A])(
      implicit executor: ExecutionContext) {

    def flatMap[B](f: A => Future[Either[E, B]]) =
      underlying.flatMap { iter =>
        Future.sequence(iter.map(f)).map(flatRight)
      }

    def map[B](f: A => B) =
      underlying.flatMap { iter =>
        Future.successful(Right(iter.map(f)))
      }

    def foreach(f: A => Unit) {
      underlying.foreach {
        _.foreach(f)
      }
    }

    def flatten = new Flatten(parent, underlying)

    def filter(p: A => Boolean) = withFilter(p)

    def withFilter(p: A => Boolean) =
      new Values(parent, underlying.map {
        _.filter(p)
      }.right)
  }
}
