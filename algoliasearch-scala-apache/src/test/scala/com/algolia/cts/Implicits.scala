package com.algolia.cts

import com.algolia.requester.ApacheRequester

object Implicits {
  implicit val testingRequester: ApacheRequester = ApacheRequester()
}
