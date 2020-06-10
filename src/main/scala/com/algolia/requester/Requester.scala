package com.algolia.requester

import com.algolia.errors.AlgoliaError
import com.algolia.requester.Requester.Identity
import com.algolia.transport.Method

import scala.concurrent.{ExecutionContext, Future}

trait Requester[F[_]] {
  def request[T: Manifest, U: Manifest](
      method: Method,
      host: String,
      path: String,
      body: T,
  ): F[Either[AlgoliaError, U]]
}

trait Functor[F[_]] {
  def apply[A](a: A): F[A]
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Requester {
  type Identity[X] = X
}

object Functor {

  def apply[F[_]: Functor](): Functor[F] = implicitly[Functor[F]]

  implicit def IdentityFunctor: Functor[Identity] =
    new Functor[Identity] {
      override def apply[A](a: A): Identity[A] = a
      override def map[A, B](fa: Identity[A])(f: A => B): Identity[B] = f(fa)
    }

  implicit def FutureFunctor(implicit ec: ExecutionContext = ExecutionContext.Implicits.global): Functor[Future] =
    new Functor[Future] {
      override def apply[A](a: A): Future[A] = Future(a)
      override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)
    }
}
