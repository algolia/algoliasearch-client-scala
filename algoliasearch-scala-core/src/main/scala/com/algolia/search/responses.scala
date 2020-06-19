package com.algolia.search

object responses {
  case class SaveObjectRes(
      createdAt: String, // TODO convert to time object
      objectID: String,
      taskID: Long
  )
}
