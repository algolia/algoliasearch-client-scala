package com.algolia.search

import com.algolia.errors.AlgoliaError
import com.algolia.requester.Requester
import com.algolia.search.responses.SaveObjectRes
import com.algolia.transport.Call.Write
import com.algolia.transport.{POST, Transport}

import scala.concurrent.{ExecutionContext, Future}

class Index[T: Manifest](
    transport: Transport,
    indexName: String
)(
    implicit
    requester: Requester,
    ex: ExecutionContext
) {

  private def path(endpoint: String): String = {
    val urlEncodedIndexName = Transport.urlEncode(indexName)
    val urlEncodedEndpoint = Transport.urlEncode(endpoint)
    s"/1/indexes/$urlEncodedIndexName$urlEncodedEndpoint"
  }

  def saveObject(obj: T): Future[Either[AlgoliaError, SaveObjectRes]] = {
    val p = path("")
    transport.request[T, SaveObjectRes](POST, p, Some(obj), Write)
  }

}
