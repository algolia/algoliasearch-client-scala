/** Code generated by OpenAPI Generator (https://openapi-generator.tech), manual changes will be lost - read more on
  * https://github.com/algolia/api-clients-automation. DO NOT EDIT.
  */
package algoliasearch.api

import algoliasearch.ingestion.ActionType._
import algoliasearch.ingestion.Authentication
import algoliasearch.ingestion.AuthenticationCreate
import algoliasearch.ingestion.AuthenticationCreateResponse
import algoliasearch.ingestion.AuthenticationSearch
import algoliasearch.ingestion.AuthenticationSortKeys._
import algoliasearch.ingestion.AuthenticationType._
import algoliasearch.ingestion.AuthenticationUpdate
import algoliasearch.ingestion.AuthenticationUpdateResponse
import algoliasearch.ingestion.DeleteResponse
import algoliasearch.ingestion.Destination
import algoliasearch.ingestion.DestinationCreate
import algoliasearch.ingestion.DestinationCreateResponse
import algoliasearch.ingestion.DestinationSearch
import algoliasearch.ingestion.DestinationSortKeys._
import algoliasearch.ingestion.DestinationType._
import algoliasearch.ingestion.DestinationUpdate
import algoliasearch.ingestion.DestinationUpdateResponse
import algoliasearch.ingestion.DockerSourceDiscover
import algoliasearch.ingestion.DockerSourceStreams
import algoliasearch.ingestion.ErrorBase
import algoliasearch.ingestion.Event
import algoliasearch.ingestion.EventSortKeys._
import algoliasearch.ingestion.EventStatus._
import algoliasearch.ingestion.EventType._
import algoliasearch.ingestion.ListAuthenticationsResponse
import algoliasearch.ingestion.ListDestinationsResponse
import algoliasearch.ingestion.ListEventsResponse
import algoliasearch.ingestion.ListSourcesResponse
import algoliasearch.ingestion.ListTasksResponse
import algoliasearch.ingestion.OrderKeys._
import algoliasearch.ingestion.PlatformWithNone
import algoliasearch.ingestion.Run
import algoliasearch.ingestion.RunListResponse
import algoliasearch.ingestion.RunResponse
import algoliasearch.ingestion.RunSortKeys._
import algoliasearch.ingestion.RunStatus._
import algoliasearch.ingestion.Source
import algoliasearch.ingestion.SourceCreate
import algoliasearch.ingestion.SourceCreateResponse
import algoliasearch.ingestion.SourceSearch
import algoliasearch.ingestion.SourceSortKeys._
import algoliasearch.ingestion.SourceType._
import algoliasearch.ingestion.SourceUpdate
import algoliasearch.ingestion.SourceUpdateResponse
import algoliasearch.ingestion.Task
import algoliasearch.ingestion.TaskCreate
import algoliasearch.ingestion.TaskCreateResponse
import algoliasearch.ingestion.TaskSearch
import algoliasearch.ingestion.TaskSortKeys._
import algoliasearch.ingestion.TaskUpdate
import algoliasearch.ingestion.TaskUpdateResponse
import algoliasearch.ingestion.TriggerType._
import algoliasearch.ingestion._
import algoliasearch.ApiClient
import algoliasearch.api.IngestionClient.hosts
import algoliasearch.config._
import algoliasearch.internal.util._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

object IngestionClient {

  /** Creates a new SearchApi instance using default hosts.
    *
    * @param appId
    *   application ID
    * @param apiKey
    *   api key
    * @param region
    *   region
    * @param clientOptions
    *   client options
    */
  def apply(
      appId: String,
      apiKey: String,
      region: String,
      clientOptions: ClientOptions = ClientOptions()
  ) = new IngestionClient(
    appId = appId,
    apiKey = apiKey,
    region = region,
    clientOptions = clientOptions
  )

  private def hosts(region: String): Seq[Host] = {
    val allowedRegions = Seq("eu", "us")
    if (!allowedRegions.contains(region)) {
      throw new IllegalArgumentException(
        s"`region` is required and must be one of the following: ${allowedRegions.mkString(", ")}"
      )
    }
    val url = "data.{region}.algolia.com".replace("{region}", region)
    Seq(Host(url = url, callTypes = Set(CallType.Read, CallType.Write)))
  }
}

class IngestionClient(
    appId: String,
    apiKey: String,
    region: String,
    clientOptions: ClientOptions = ClientOptions()
) extends ApiClient(
      appId = appId,
      apiKey = apiKey,
      clientName = "Ingestion",
      defaultHosts = hosts(region),
      formats = JsonSupport.format,
      options = clientOptions
    ) {

  /** Create a authentication.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param authenticationCreate
    */
  def createAuthentication(authenticationCreate: AuthenticationCreate, requestOptions: Option[RequestOptions] = None)(
      implicit ec: ExecutionContext
  ): Future[AuthenticationCreateResponse] = Future {
    requireNotNull(
      authenticationCreate,
      "Parameter `authenticationCreate` is required when calling `createAuthentication`."
    )

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/authentications")
      .withBody(authenticationCreate)
      .build()
    execute[AuthenticationCreateResponse](request, requestOptions)
  }

  /** Create a destination.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param destinationCreate
    */
  def createDestination(destinationCreate: DestinationCreate, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DestinationCreateResponse] = Future {
    requireNotNull(destinationCreate, "Parameter `destinationCreate` is required when calling `createDestination`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/destinations")
      .withBody(destinationCreate)
      .build()
    execute[DestinationCreateResponse](request, requestOptions)
  }

  /** Create a source.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceCreate
    */
  def createSource(sourceCreate: SourceCreate, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[SourceCreateResponse] = Future {
    requireNotNull(sourceCreate, "Parameter `sourceCreate` is required when calling `createSource`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/sources")
      .withBody(sourceCreate)
      .build()
    execute[SourceCreateResponse](request, requestOptions)
  }

  /** Create a task.
    *
    * @param taskCreate
    */
  def createTask(taskCreate: TaskCreate, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[TaskCreateResponse] = Future {
    requireNotNull(taskCreate, "Parameter `taskCreate` is required when calling `createTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/tasks")
      .withBody(taskCreate)
      .build()
    execute[TaskCreateResponse](request, requestOptions)
  }

  /** This method allow you to send requests to the Algolia REST API.
    *
    * @param path
    *   Path of the endpoint, anything after \"/1\" must be specified.
    * @param parameters
    *   Query parameters to apply to the current query.
    */
  def customDelete[T: Manifest](
      path: String,
      parameters: Option[Map[String, Any]] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[T] = Future {
    requireNotNull(path, "Parameter `path` is required when calling `customDelete`.")

    val request = HttpRequest
      .builder()
      .withMethod("DELETE")
      .withPath(s"/${path}")
      .withQueryParameters(parameters)
      .build()
    execute[T](request, requestOptions)
  }

  /** This method allow you to send requests to the Algolia REST API.
    *
    * @param path
    *   Path of the endpoint, anything after \"/1\" must be specified.
    * @param parameters
    *   Query parameters to apply to the current query.
    */
  def customGet[T: Manifest](
      path: String,
      parameters: Option[Map[String, Any]] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[T] = Future {
    requireNotNull(path, "Parameter `path` is required when calling `customGet`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/${path}")
      .withQueryParameters(parameters)
      .build()
    execute[T](request, requestOptions)
  }

  /** This method allow you to send requests to the Algolia REST API.
    *
    * @param path
    *   Path of the endpoint, anything after \"/1\" must be specified.
    * @param parameters
    *   Query parameters to apply to the current query.
    * @param body
    *   Parameters to send with the custom request.
    */
  def customPost[T: Manifest](
      path: String,
      parameters: Option[Map[String, Any]] = None,
      body: Option[Any] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[T] = Future {
    requireNotNull(path, "Parameter `path` is required when calling `customPost`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/${path}")
      .withBody(body)
      .withQueryParameters(parameters)
      .build()
    execute[T](request, requestOptions)
  }

  /** This method allow you to send requests to the Algolia REST API.
    *
    * @param path
    *   Path of the endpoint, anything after \"/1\" must be specified.
    * @param parameters
    *   Query parameters to apply to the current query.
    * @param body
    *   Parameters to send with the custom request.
    */
  def customPut[T: Manifest](
      path: String,
      parameters: Option[Map[String, Any]] = None,
      body: Option[Any] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[T] = Future {
    requireNotNull(path, "Parameter `path` is required when calling `customPut`.")

    val request = HttpRequest
      .builder()
      .withMethod("PUT")
      .withPath(s"/${path}")
      .withBody(body)
      .withQueryParameters(parameters)
      .build()
    execute[T](request, requestOptions)
  }

  /** Soft delete the authentication of the given authenticationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param authenticationID
    *   The authentication UUID.
    */
  def deleteAuthentication(authenticationID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DeleteResponse] = Future {
    requireNotNull(authenticationID, "Parameter `authenticationID` is required when calling `deleteAuthentication`.")

    val request = HttpRequest
      .builder()
      .withMethod("DELETE")
      .withPath(s"/1/authentications/${escape(authenticationID)}")
      .build()
    execute[DeleteResponse](request, requestOptions)
  }

  /** Soft delete the destination of the given destinationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param destinationID
    *   The destination UUID.
    */
  def deleteDestination(destinationID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DeleteResponse] = Future {
    requireNotNull(destinationID, "Parameter `destinationID` is required when calling `deleteDestination`.")

    val request = HttpRequest
      .builder()
      .withMethod("DELETE")
      .withPath(s"/1/destinations/${escape(destinationID)}")
      .build()
    execute[DeleteResponse](request, requestOptions)
  }

  /** Soft delete the source of the given sourceID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceID
    *   The source UUID.
    */
  def deleteSource(sourceID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DeleteResponse] = Future {
    requireNotNull(sourceID, "Parameter `sourceID` is required when calling `deleteSource`.")

    val request = HttpRequest
      .builder()
      .withMethod("DELETE")
      .withPath(s"/1/sources/${escape(sourceID)}")
      .build()
    execute[DeleteResponse](request, requestOptions)
  }

  /** Soft delete the task of the given taskID.
    *
    * @param taskID
    *   The task UUID.
    */
  def deleteTask(taskID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DeleteResponse] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `deleteTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("DELETE")
      .withPath(s"/1/tasks/${escape(taskID)}")
      .build()
    execute[DeleteResponse](request, requestOptions)
  }

  /** Disable the task of the given taskID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param taskID
    *   The task UUID.
    */
  def disableTask(taskID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[TaskUpdateResponse] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `disableTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("PUT")
      .withPath(s"/1/tasks/${escape(taskID)}/disable")
      .build()
    execute[TaskUpdateResponse](request, requestOptions)
  }

  /** Enable the task of the given taskID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param taskID
    *   The task UUID.
    */
  def enableTask(taskID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[TaskUpdateResponse] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `enableTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("PUT")
      .withPath(s"/1/tasks/${escape(taskID)}/enable")
      .build()
    execute[TaskUpdateResponse](request, requestOptions)
  }

  /** Get the authentication of the given authenticationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param authenticationID
    *   The authentication UUID.
    */
  def getAuthentication(authenticationID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Authentication] = Future {
    requireNotNull(authenticationID, "Parameter `authenticationID` is required when calling `getAuthentication`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/authentications/${escape(authenticationID)}")
      .build()
    execute[Authentication](request, requestOptions)
  }

  /** Get a list of authentications for the given query parameters, with pagination details.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param `type`
    *   The type of the authentications to retrieve.
    * @param platform
    *   The platform of the authentications to retrieve.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    */
  def getAuthentications(
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      `type`: Option[Seq[AuthenticationType]] = None,
      platform: Option[Seq[PlatformWithNone]] = None,
      sort: Option[AuthenticationSortKeys] = None,
      order: Option[OrderKeys] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[ListAuthenticationsResponse] = Future {

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/authentications")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("type", `type`)
      .withQueryParameter("platform", platform)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .build()
    execute[ListAuthenticationsResponse](request, requestOptions)
  }

  /** Get the destination of the given destinationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param destinationID
    *   The destination UUID.
    */
  def getDestination(destinationID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Destination] = Future {
    requireNotNull(destinationID, "Parameter `destinationID` is required when calling `getDestination`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/destinations/${escape(destinationID)}")
      .build()
    execute[Destination](request, requestOptions)
  }

  /** Get a list of destinations for the given query parameters, with pagination details.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param `type`
    *   The type of the destinations to retrive.
    * @param authenticationID
    *   The authenticationIDs of the destinations to retrive.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    */
  def getDestinations(
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      `type`: Option[Seq[DestinationType]] = None,
      authenticationID: Option[Seq[String]] = None,
      sort: Option[DestinationSortKeys] = None,
      order: Option[OrderKeys] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[ListDestinationsResponse] = Future {

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/destinations")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("type", `type`)
      .withQueryParameter("authenticationID", authenticationID)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .build()
    execute[ListDestinationsResponse](request, requestOptions)
  }

  /** Retrieve a stream listing for a given Singer specification compatible docker type source ID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceID
    *   The source UUID.
    */
  def getDockerSourceStreams(sourceID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DockerSourceStreams] = Future {
    requireNotNull(sourceID, "Parameter `sourceID` is required when calling `getDockerSourceStreams`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/sources/${escape(sourceID)}/discover")
      .build()
    execute[DockerSourceStreams](request, requestOptions)
  }

  /** Get a single event for a specific runID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param runID
    *   The run UUID.
    * @param eventID
    *   The event UUID.
    */
  def getEvent(runID: String, eventID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Event] = Future {
    requireNotNull(runID, "Parameter `runID` is required when calling `getEvent`.")
    requireNotNull(eventID, "Parameter `eventID` is required when calling `getEvent`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/runs/${escape(runID)}/events/${escape(eventID)}")
      .build()
    execute[Event](request, requestOptions)
  }

  /** Get a list of events associated to the given runID, for the given query parameters.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param runID
    *   The run UUID.
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param status
    *   Filter the status of the events.
    * @param `type`
    *   Filter the type of the events.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    * @param startDate
    *   The start date (in RFC3339 format) of the events fetching window. Defaults to 'now'-3 hours if omitted.
    * @param endDate
    *   The end date (in RFC3339 format) of the events fetching window. Defaults to 'now' days if omitted.
    */
  def getEvents(
      runID: String,
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      status: Option[Seq[EventStatus]] = None,
      `type`: Option[Seq[EventType]] = None,
      sort: Option[EventSortKeys] = None,
      order: Option[OrderKeys] = None,
      startDate: Option[String] = None,
      endDate: Option[String] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[ListEventsResponse] = Future {
    requireNotNull(runID, "Parameter `runID` is required when calling `getEvents`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/runs/${escape(runID)}/events")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("status", status)
      .withQueryParameter("type", `type`)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .withQueryParameter("startDate", startDate)
      .withQueryParameter("endDate", endDate)
      .build()
    execute[ListEventsResponse](request, requestOptions)
  }

  /** Get a single run for the given ID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param runID
    *   The run UUID.
    */
  def getRun(runID: String, requestOptions: Option[RequestOptions] = None)(implicit ec: ExecutionContext): Future[Run] =
    Future {
      requireNotNull(runID, "Parameter `runID` is required when calling `getRun`.")

      val request = HttpRequest
        .builder()
        .withMethod("GET")
        .withPath(s"/1/runs/${escape(runID)}")
        .build()
      execute[Run](request, requestOptions)
    }

  /** Get a list of runs for the given query parameters, with pagination details.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param status
    *   Filter the status of the runs.
    * @param taskID
    *   Filter by taskID.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    * @param startDate
    *   The start date (in RFC3339 format) of the runs fetching window. Defaults to 'now'-7 days if omitted.
    * @param endDate
    *   The end date (in RFC3339 format) of the runs fetching window. Defaults to 'now' days if omitted.
    */
  def getRuns(
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      status: Option[Seq[RunStatus]] = None,
      taskID: Option[String] = None,
      sort: Option[RunSortKeys] = None,
      order: Option[OrderKeys] = None,
      startDate: Option[String] = None,
      endDate: Option[String] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[RunListResponse] = Future {

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/runs")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("status", status)
      .withQueryParameter("taskID", taskID)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .withQueryParameter("startDate", startDate)
      .withQueryParameter("endDate", endDate)
      .build()
    execute[RunListResponse](request, requestOptions)
  }

  /** Get the source of the given sourceID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceID
    *   The source UUID.
    */
  def getSource(sourceID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Source] = Future {
    requireNotNull(sourceID, "Parameter `sourceID` is required when calling `getSource`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/sources/${escape(sourceID)}")
      .build()
    execute[Source](request, requestOptions)
  }

  /** Get a list of sources for the given query parameters, with pagination details.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param `type`
    *   The type of the sources to retrieve.
    * @param authenticationID
    *   The authenticationIDs of the sources to retrieve. 'none' returns sources that doesn't have an authentication.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    */
  def getSources(
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      `type`: Option[Seq[SourceType]] = None,
      authenticationID: Option[Seq[String]] = None,
      sort: Option[SourceSortKeys] = None,
      order: Option[OrderKeys] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[ListSourcesResponse] = Future {

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/sources")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("type", `type`)
      .withQueryParameter("authenticationID", authenticationID)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .build()
    execute[ListSourcesResponse](request, requestOptions)
  }

  /** Get the task of the given taskID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param taskID
    *   The task UUID.
    */
  def getTask(taskID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Task] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `getTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/tasks/${escape(taskID)}")
      .build()
    execute[Task](request, requestOptions)
  }

  /** Get a list of tasks for the given query parameters, with pagination details.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param itemsPerPage
    *   The number of items per page to return.
    * @param page
    *   The page number to fetch, starting at 1.
    * @param action
    *   The action of the tasks to retrieve.
    * @param enabled
    *   Whether the task is enabled or not.
    * @param sourceID
    *   The sourceIDs of the tasks to retrieve.
    * @param destinationID
    *   The destinationIDs of the tasks to retrieve.
    * @param triggerType
    *   The trigger type of the task.
    * @param sort
    *   The key by which the list should be sorted.
    * @param order
    *   The order of the returned list.
    */
  def getTasks(
      itemsPerPage: Option[Int] = None,
      page: Option[Int] = None,
      action: Option[Seq[ActionType]] = None,
      enabled: Option[Boolean] = None,
      sourceID: Option[Seq[String]] = None,
      destinationID: Option[Seq[String]] = None,
      triggerType: Option[Seq[TriggerType]] = None,
      sort: Option[TaskSortKeys] = None,
      order: Option[OrderKeys] = None,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[ListTasksResponse] = Future {

    val request = HttpRequest
      .builder()
      .withMethod("GET")
      .withPath(s"/1/tasks")
      .withQueryParameter("itemsPerPage", itemsPerPage)
      .withQueryParameter("page", page)
      .withQueryParameter("action", action)
      .withQueryParameter("enabled", enabled)
      .withQueryParameter("sourceID", sourceID)
      .withQueryParameter("destinationID", destinationID)
      .withQueryParameter("triggerType", triggerType)
      .withQueryParameter("sort", sort)
      .withQueryParameter("order", order)
      .build()
    execute[ListTasksResponse](request, requestOptions)
  }

  /** Run the task of the given taskID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param taskID
    *   The task UUID.
    */
  def runTask(taskID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[RunResponse] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `runTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/tasks/${escape(taskID)}/run")
      .build()
    execute[RunResponse](request, requestOptions)
  }

  /** Search among authentications with a defined set of parameters.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    */
  def searchAuthentications(authenticationSearch: AuthenticationSearch, requestOptions: Option[RequestOptions] = None)(
      implicit ec: ExecutionContext
  ): Future[Seq[Authentication]] = Future {
    requireNotNull(
      authenticationSearch,
      "Parameter `authenticationSearch` is required when calling `searchAuthentications`."
    )

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/authentications/search")
      .withBody(authenticationSearch)
      .build()
    execute[Seq[Authentication]](request, requestOptions)
  }

  /** Search among destinations with a defined set of parameters.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    */
  def searchDestinations(destinationSearch: DestinationSearch, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Seq[Destination]] = Future {
    requireNotNull(destinationSearch, "Parameter `destinationSearch` is required when calling `searchDestinations`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/destinations/search")
      .withBody(destinationSearch)
      .build()
    execute[Seq[Destination]](request, requestOptions)
  }

  /** Search among sources with a defined set of parameters.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    */
  def searchSources(sourceSearch: SourceSearch, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Seq[Source]] = Future {
    requireNotNull(sourceSearch, "Parameter `sourceSearch` is required when calling `searchSources`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/sources/search")
      .withBody(sourceSearch)
      .build()
    execute[Seq[Source]](request, requestOptions)
  }

  /** Search among tasks with a defined set of parameters.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    */
  def searchTasks(taskSearch: TaskSearch, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[Seq[Task]] = Future {
    requireNotNull(taskSearch, "Parameter `taskSearch` is required when calling `searchTasks`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/tasks/search")
      .withBody(taskSearch)
      .build()
    execute[Seq[Task]](request, requestOptions)
  }

  /** Trigger a stream listing request for a Singer specification compatible docker type source.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceID
    *   The source UUID.
    */
  def triggerDockerSourceDiscover(sourceID: String, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[DockerSourceDiscover] = Future {
    requireNotNull(sourceID, "Parameter `sourceID` is required when calling `triggerDockerSourceDiscover`.")

    val request = HttpRequest
      .builder()
      .withMethod("POST")
      .withPath(s"/1/sources/${escape(sourceID)}/discover")
      .build()
    execute[DockerSourceDiscover](request, requestOptions)
  }

  /** Update the authentication of the given authenticationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param authenticationID
    *   The authentication UUID.
    */
  def updateAuthentication(
      authenticationID: String,
      authenticationUpdate: AuthenticationUpdate,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[AuthenticationUpdateResponse] = Future {
    requireNotNull(authenticationID, "Parameter `authenticationID` is required when calling `updateAuthentication`.")
    requireNotNull(
      authenticationUpdate,
      "Parameter `authenticationUpdate` is required when calling `updateAuthentication`."
    )

    val request = HttpRequest
      .builder()
      .withMethod("PATCH")
      .withPath(s"/1/authentications/${escape(authenticationID)}")
      .withBody(authenticationUpdate)
      .build()
    execute[AuthenticationUpdateResponse](request, requestOptions)
  }

  /** Update the destination of the given destinationID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param destinationID
    *   The destination UUID.
    */
  def updateDestination(
      destinationID: String,
      destinationUpdate: DestinationUpdate,
      requestOptions: Option[RequestOptions] = None
  )(implicit ec: ExecutionContext): Future[DestinationUpdateResponse] = Future {
    requireNotNull(destinationID, "Parameter `destinationID` is required when calling `updateDestination`.")
    requireNotNull(destinationUpdate, "Parameter `destinationUpdate` is required when calling `updateDestination`.")

    val request = HttpRequest
      .builder()
      .withMethod("PATCH")
      .withPath(s"/1/destinations/${escape(destinationID)}")
      .withBody(destinationUpdate)
      .build()
    execute[DestinationUpdateResponse](request, requestOptions)
  }

  /** Update the source of the given sourceID.
    *
    * Required API Key ACLs:
    *   - addObject
    *   - deleteIndex
    *   - editSettings
    *
    * @param sourceID
    *   The source UUID.
    */
  def updateSource(sourceID: String, sourceUpdate: SourceUpdate, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[SourceUpdateResponse] = Future {
    requireNotNull(sourceID, "Parameter `sourceID` is required when calling `updateSource`.")
    requireNotNull(sourceUpdate, "Parameter `sourceUpdate` is required when calling `updateSource`.")

    val request = HttpRequest
      .builder()
      .withMethod("PATCH")
      .withPath(s"/1/sources/${escape(sourceID)}")
      .withBody(sourceUpdate)
      .build()
    execute[SourceUpdateResponse](request, requestOptions)
  }

  /** Update the task of the given taskID.
    *
    * @param taskID
    *   The task UUID.
    */
  def updateTask(taskID: String, taskUpdate: TaskUpdate, requestOptions: Option[RequestOptions] = None)(implicit
      ec: ExecutionContext
  ): Future[TaskUpdateResponse] = Future {
    requireNotNull(taskID, "Parameter `taskID` is required when calling `updateTask`.")
    requireNotNull(taskUpdate, "Parameter `taskUpdate` is required when calling `updateTask`.")

    val request = HttpRequest
      .builder()
      .withMethod("PATCH")
      .withPath(s"/1/tasks/${escape(taskID)}")
      .withBody(taskUpdate)
      .build()
    execute[TaskUpdateResponse](request, requestOptions)
  }

}
