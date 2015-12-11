package algolia.inputs

case class BatchOperations(requests: Seq[BatchOperation[_ <: AnyRef]])

sealed trait BatchOperation[T <: AnyRef] {

  val action: String

}


case class AddObjectOperation[T <: AnyRef](body: T,
                                           indexName: Option[String] = None,
                                           action: String = "addObject") extends BatchOperation[T]

case class UpdateObjectOperation[T <: AnyRef](body: T,
                                              indexName: Option[String] = None,
                                              action: String = "updateObject") extends BatchOperation[T]

//case class PartialUpdateObjectOperation[T <: AnyRef](body: T, override val indexName: Option[String] = None, action: String = "partialUpdateObject") extends BatchOperation[T]

//case class PartialUpdateObjectNoCreateOperation[T <: AnyRef](body: T, override val indexName: Option[String] = None, action: String = "partialUpdateObjectNoCreate") extends BatchOperation[T]

case class DeleteObjectOperation[T <: AnyRef](indexName: String,
                                              objectID: String,
                                              action: String = "deleteObject") extends BatchOperation[T]

case class ClearIndexOperation[T <: AnyRef](indexName: String,
                                            action: String = "clear") extends BatchOperation[T]
