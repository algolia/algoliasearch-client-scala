package algolia.responses

trait TasksSingleIndex {

  def taskID: Int

  def objectIDs: Seq[String]

}

trait TasksMultipleIndex {

  def taskID: Map[String, Int]

  def objectIDs: Seq[String]

}
