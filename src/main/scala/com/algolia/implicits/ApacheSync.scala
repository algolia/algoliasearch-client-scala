package com.algolia.implicits

import com.algolia.requester.Requester.Identity
import com.algolia.requester.{ApacheAsyncRequester, ApacheSyncRequester, Functor}

import scala.concurrent.{ExecutionContext, Future}

object ApacheSync {
  implicit val requester: ApacheSyncRequester = ApacheSyncRequester()
  implicit val functor: Functor[Identity] = Functor.IdentityFunctor
}

object ApacheAsync {
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  implicit val requester: ApacheAsyncRequester = ApacheAsyncRequester()
  implicit val functor: Functor[Future] = Functor.FutureFunctor
}
