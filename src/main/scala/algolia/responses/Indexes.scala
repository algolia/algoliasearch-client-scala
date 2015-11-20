package algolia.responses

case class Indexes(items: Seq[Index], nbPages: Int)

case class Index(name: String,
                 createdAt: String,
                 updatedAt: String,
                 entries: Int,
                 dataSize: Int,
                 fileSize: Int,
                 lastBuildTimeS: Int,
                 numberOfPendingTask: Int,
                 pendingTask: Boolean)