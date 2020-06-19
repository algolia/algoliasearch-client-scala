package com.algolia.cts

import com.algolia.requester.ApacheSyncRequester._
import com.algolia.search.Client

class Indexing extends IndexingBase {
  override lazy val client1 = Client(Utils.appId1, Utils.adminKey1)
}
