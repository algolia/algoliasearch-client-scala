package algolia.responses

case class Indexing(createdAt: Option[String],
                    taskID: Int,
                    objectID: String) extends ObjectID with Task
