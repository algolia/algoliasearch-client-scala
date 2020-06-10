package com.algolia.requester

//trait ResponseWrapper[A, W[_]] {
//  def map[B](f: A => B): W[B]
//}
//
//case class Identity[A](value: A) extends ResponseWrapper[A, Identity] {
//  override def map[B](f: A => B): Identity[B] =
//    Identity(f(value))
//}
