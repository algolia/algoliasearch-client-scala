package com.algolia.search

object Responses {
  case class SaveObjectRes(
      createdAt: String, // TODO convert to time object
      objectID: String,
      taskID: Long,
  )
}
