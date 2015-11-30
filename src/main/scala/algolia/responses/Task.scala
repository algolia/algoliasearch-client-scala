package algolia.responses

trait Task {

  def createdAt: Option[String]

  def taskID: Int

}