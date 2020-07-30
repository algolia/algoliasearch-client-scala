package com.algolia.requester

import com.algolia.transport.{HttpResponse, Method}

import scala.concurrent.{ExecutionContext, Future}

trait Requester {
  def request[T: Manifest](
      method: Method,
      headers: Map[String, String],
      host: String,
      path: String,
      body: Option[T]
  )(implicit ec: ExecutionContext): Future[HttpResponse]
}
