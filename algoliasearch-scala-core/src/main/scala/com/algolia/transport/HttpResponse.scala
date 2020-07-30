package com.algolia.transport

import java.io.InputStream

import scala.util.Try

case class HttpResponse(code: Int, body: Try[InputStream])
