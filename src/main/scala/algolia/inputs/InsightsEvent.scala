package algolia.inputs

case class InsightsEvent(
    eventType: String,
    eventName: String,
    index: String,
    userToken: String,
    timestamp: Option[Long] = None, // Epoch timestamp in millisecond
    objectIDs: Option[Iterable[String]] = None,
    filters: Option[Iterable[String]] = None,
    positions: Option[Iterable[Int]] = None,
    queryID: Option[String] = None
)

object ClickedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("click", eventName, indexName, userToken, filters = Some(filters))
}

object ClickedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("click", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}

object ClickedObjectIDsAfterSearch {
  def apply(userToken: String,
            eventName: String,
            indexName: String,
            objectIDs: Iterable[String],
            positions: Iterable[Int],
            queryID: String) =
    InsightsEvent("click",
                  eventName,
                  indexName,
                  userToken,
                  objectIDs = Some(objectIDs),
                  positions = Some(positions),
                  queryID = Some(queryID))
}

object ConvertedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("conversion", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}

object ConvertedObjectIDsAfterSearch {
  def apply(userToken: String,
            eventName: String,
            indexName: String,
            objectIDs: Iterable[String],
            queryID: String) =
    InsightsEvent("conversion",
                  eventName,
                  indexName,
                  userToken,
                  objectIDs = Some(objectIDs),
                  queryID = Some(queryID))
}

object ConvertedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("conversion", eventName, indexName, userToken, filters = Some(filters))
}

object ViewedFilters {
  def apply(userToken: String, eventName: String, indexName: String, filters: Iterable[String]) =
    InsightsEvent("view", eventName, indexName, userToken, filters = Some(filters))
}

object ViewedObjectIDs {
  def apply(userToken: String, eventName: String, indexName: String, objectIDs: Iterable[String]) =
    InsightsEvent("view", eventName, indexName, userToken, objectIDs = Some(objectIDs))
}
