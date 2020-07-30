package com.algolia.fp

import scala.concurrent.{ExecutionContext, Future}

trait Monad[M[_]] {
  def pure[A](a: A): M[A]
  def map[A, B](fa: M[A])(f: A => B): M[B]
//  def ap[A, B](ff: M[A => B])(fa: M[A]): M[B]
//  def flatten[A](ffa: M[M[A]]): M[A] = map(ffa)(fa => flatMap(fa)(a => a))
//  def flatMap[A, B](fa: M[A])(f: A ⇒ M[B]): M[B]
}

object Utils {

  type Identity[X] = X

//  def apply[F[_]: Monad](): Monad[F] = implicitly[Monad[F]]

  implicit def IdentityMonad: Monad[Identity] =
    new Monad[Identity] {
      override def pure[A](a: A): Identity[A] = a
      override def map[A, B](fa: Identity[A])(f: A => B): Identity[B] = f(fa)
//      override def ap[A, B](ff: Identity[A => B])(fa: Identity[A]): Identity[B] = pure(ff(fa))
//      override def flatten[A](ffa: Identity[Identity[A]]) = ff
//      override def flatMap[A, B](fa: Identity[A])(f: A => Identity[B]): Identity[B] = map(fa)(a => f(a)).flatten
    }

  implicit def FutureMonad(implicit ec: ExecutionContext = ExecutionContext.Implicits.global): Monad[Future] =
    new Monad[Future] {
      override def pure[A](a: A): Future[A] = Future.successful(a)
      override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)
//      override def ap[A, B](ff: Future[A => B])(fa: Future[A]): Future[B] = ff.flatMap(fa.map(_))
//      override def flatMap[A, B](fa: Future[A])(f: A => Future[B]) = ???
    }
}
