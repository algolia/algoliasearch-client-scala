/** Ingestion API API powering the Data Ingestion connectors of Algolia.
  *
  * The version of the OpenAPI document: 1.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.ingestion

import algoliasearch.ingestion.ActionType._

/** The payload for a task creation.
  *
  * @param sourceID
  *   The source UUID.
  * @param destinationID
  *   The destination UUID.
  * @param enabled
  *   Whether the task is enabled or not.
  * @param failureThreshold
  *   A percentage representing the accepted failure threshold to determine if a `run` succeeded or not.
  */
case class TaskCreate(
    sourceID: String,
    destinationID: String,
    trigger: TaskCreateTrigger,
    action: ActionType,
    enabled: Option[Boolean] = scala.None,
    failureThreshold: Option[Int] = scala.None,
    input: Option[TaskInput] = scala.None
)

object TaskCreateEnums {}
