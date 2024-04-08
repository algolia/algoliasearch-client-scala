/** Usage API The Usage API gives you access to statistics about all requests made to your Algolia applications. ## Base
  * URL The base URL for requests to the Usage API is: - `https://usage.algolia.com` **All requests must use HTTPS.** ##
  * Authentication To authenticate your API requests, add these headers: - `x-algolia-application-id`. Your Algolia
  * application ID. - `x-algolia-api-key`. The Usage API key. You can find your application ID and Usage API key in the
  * [Algolia dashboard](https://dashboard.algolia.com/account). ## Response status and errors The Usage API returns JSON
  * responses. Since JSON doesn't guarantee any specific ordering, don't rely on the order of attributes in the API
  * response. Successful responses return a `2xx` status. Client errors return a `4xx` status. Server errors are
  * indicated by a `5xx` status. Error responses have a `message` property with more information. ## Version The current
  * version of the Usage API is version 1, as indicated by the `/1/` in each endpoint's URL.
  *
  * The version of the OpenAPI document: 1.0.0
  *
  * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
  * https://openapi-generator.tech Do not edit the class manually.
  */
package algoliasearch.usage

import org.json4s._

sealed trait Statistic

/** Statistic enumeration
  */
object Statistic {
  case object Star extends Statistic {
    override def toString = "*"
  }
  case object SearchOperations extends Statistic {
    override def toString = "search_operations"
  }
  case object TotalSearchOperations extends Statistic {
    override def toString = "total_search_operations"
  }
  case object TotalSearchRequests extends Statistic {
    override def toString = "total_search_requests"
  }
  case object QueriesOperations extends Statistic {
    override def toString = "queries_operations"
  }
  case object MultiQueriesOperations extends Statistic {
    override def toString = "multi_queries_operations"
  }
  case object AclOperations extends Statistic {
    override def toString = "acl_operations"
  }
  case object TotalAclOperations extends Statistic {
    override def toString = "total_acl_operations"
  }
  case object GetApiKeysOperations extends Statistic {
    override def toString = "get_api_keys_operations"
  }
  case object GetApiKeyOperations extends Statistic {
    override def toString = "get_api_key_operations"
  }
  case object AddApiKeyOperations extends Statistic {
    override def toString = "add_api_key_operations"
  }
  case object UpdateApiKeyOperations extends Statistic {
    override def toString = "update_api_key_operations"
  }
  case object DeleteApiKeyOperations extends Statistic {
    override def toString = "delete_api_key_operations"
  }
  case object ListApiKeyOperations extends Statistic {
    override def toString = "list_api_key_operations"
  }
  case object IndexingOperations extends Statistic {
    override def toString = "indexing_operations"
  }
  case object TotalIndexingOperations extends Statistic {
    override def toString = "total_indexing_operations"
  }
  case object BrowseOperations extends Statistic {
    override def toString = "browse_operations"
  }
  case object ClearIndexOperations extends Statistic {
    override def toString = "clear_index_operations"
  }
  case object CopyMoveOperations extends Statistic {
    override def toString = "copy_move_operations"
  }
  case object DeleteIndexOperations extends Statistic {
    override def toString = "delete_index_operations"
  }
  case object GetLogOperations extends Statistic {
    override def toString = "get_log_operations"
  }
  case object GetSettingsOperations extends Statistic {
    override def toString = "get_settings_operations"
  }
  case object SetSettingsOperations extends Statistic {
    override def toString = "set_settings_operations"
  }
  case object ListIndicesOperations extends Statistic {
    override def toString = "list_indices_operations"
  }
  case object WaitTaskOperations extends Statistic {
    override def toString = "wait_task_operations"
  }
  case object RecordOperations extends Statistic {
    override def toString = "record_operations"
  }
  case object TotalRecordsOperations extends Statistic {
    override def toString = "total_records_operations"
  }
  case object AddRecordOperations extends Statistic {
    override def toString = "add_record_operations"
  }
  case object BatchOperations extends Statistic {
    override def toString = "batch_operations"
  }
  case object DeleteByQueryOperations extends Statistic {
    override def toString = "delete_by_query_operations"
  }
  case object DeleteRecordOperations extends Statistic {
    override def toString = "delete_record_operations"
  }
  case object GetRecordOperations extends Statistic {
    override def toString = "get_record_operations"
  }
  case object PartialUpdateRecordOperations extends Statistic {
    override def toString = "partial_update_record_operations"
  }
  case object UpdateRecordOperations extends Statistic {
    override def toString = "update_record_operations"
  }
  case object SynonymOperations extends Statistic {
    override def toString = "synonym_operations"
  }
  case object TotalSynonymOperations extends Statistic {
    override def toString = "total_synonym_operations"
  }
  case object BatchSynonymOperations extends Statistic {
    override def toString = "batch_synonym_operations"
  }
  case object ClearSynonymOperations extends Statistic {
    override def toString = "clear_synonym_operations"
  }
  case object DeleteSynonymOperations extends Statistic {
    override def toString = "delete_synonym_operations"
  }
  case object GetSynonymOperations extends Statistic {
    override def toString = "get_synonym_operations"
  }
  case object QuerySynonymOperations extends Statistic {
    override def toString = "query_synonym_operations"
  }
  case object UpdateSynonymOperations extends Statistic {
    override def toString = "update_synonym_operations"
  }
  case object RuleOperations extends Statistic {
    override def toString = "rule_operations"
  }
  case object TotalRulesOperations extends Statistic {
    override def toString = "total_rules_operations"
  }
  case object BatchRulesOperations extends Statistic {
    override def toString = "batch_rules_operations"
  }
  case object ClearRulesOperations extends Statistic {
    override def toString = "clear_rules_operations"
  }
  case object DeleteRulesOperations extends Statistic {
    override def toString = "delete_rules_operations"
  }
  case object GetRulesOperations extends Statistic {
    override def toString = "get_rules_operations"
  }
  case object SaveRulesOperations extends Statistic {
    override def toString = "save_rules_operations"
  }
  case object SearchRulesOperations extends Statistic {
    override def toString = "search_rules_operations"
  }
  case object TotalRecommendRequests extends Statistic {
    override def toString = "total_recommend_requests"
  }
  case object TotalWriteOperations extends Statistic {
    override def toString = "total_write_operations"
  }
  case object TotalReadOperations extends Statistic {
    override def toString = "total_read_operations"
  }
  case object TotalOperations extends Statistic {
    override def toString = "total_operations"
  }
  case object QuerysuggestionsTotalSearchOperations extends Statistic {
    override def toString = "querysuggestions_total_search_operations"
  }
  case object QuerysuggestionsTotalSearchRequests extends Statistic {
    override def toString = "querysuggestions_total_search_requests"
  }
  case object QuerysuggestionsTotalAclOperations extends Statistic {
    override def toString = "querysuggestions_total_acl_operations"
  }
  case object QuerysuggestionsTotalIndexingOperations extends Statistic {
    override def toString = "querysuggestions_total_indexing_operations"
  }
  case object QuerysuggestionsTotalRecordsOperations extends Statistic {
    override def toString = "querysuggestions_total_records_operations"
  }
  case object QuerysuggestionsTotalSynonymOperations extends Statistic {
    override def toString = "querysuggestions_total_synonym_operations"
  }
  case object QuerysuggestionsTotalRulesOperations extends Statistic {
    override def toString = "querysuggestions_total_rules_operations"
  }
  case object QuerysuggestionsTotalWriteOperations extends Statistic {
    override def toString = "querysuggestions_total_write_operations"
  }
  case object QuerysuggestionsTotalReadOperations extends Statistic {
    override def toString = "querysuggestions_total_read_operations"
  }
  case object QuerysuggestionsTotalOperations extends Statistic {
    override def toString = "querysuggestions_total_operations"
  }
  case object AvgProcessingTime extends Statistic {
    override def toString = "avg_processing_time"
  }
  case object `90pProcessingTime` extends Statistic {
    override def toString = "90p_processing_time"
  }
  case object `99pProcessingTime` extends Statistic {
    override def toString = "99p_processing_time"
  }
  case object QueriesAboveLastMsProcessingTime extends Statistic {
    override def toString = "queries_above_last_ms_processing_time"
  }
  case object Records extends Statistic {
    override def toString = "records"
  }
  case object DataSize extends Statistic {
    override def toString = "data_size"
  }
  case object FileSize extends Statistic {
    override def toString = "file_size"
  }
  case object MaxQps extends Statistic {
    override def toString = "max_qps"
  }
  case object RegionMaxQps extends Statistic {
    override def toString = "region_max_qps"
  }
  case object TotalMaxQps extends Statistic {
    override def toString = "total_max_qps"
  }
  case object UsedSearchCapacity extends Statistic {
    override def toString = "used_search_capacity"
  }
  case object AvgUsedSearchCapacity extends Statistic {
    override def toString = "avg_used_search_capacity"
  }
  case object RegionUsedSearchCapacity extends Statistic {
    override def toString = "region_used_search_capacity"
  }
  case object RegionAvgUsedSearchCapacity extends Statistic {
    override def toString = "region_avg_used_search_capacity"
  }
  case object TotalUsedSearchCapacity extends Statistic {
    override def toString = "total_used_search_capacity"
  }
  case object TotalAvgUsedSearchCapacity extends Statistic {
    override def toString = "total_avg_used_search_capacity"
  }
  case object DegradedQueriesSsdUsedQueriesImpacted extends Statistic {
    override def toString = "degraded_queries_ssd_used_queries_impacted"
  }
  case object DegradedQueriesSsdUsedSecondsImpacted extends Statistic {
    override def toString = "degraded_queries_ssd_used_seconds_impacted"
  }
  case object DegradedQueriesMaxCapacityQueriesImpacted extends Statistic {
    override def toString = "degraded_queries_max_capacity_queries_impacted"
  }
  case object DegradedQueriesMaxCapacitySecondsImpacted extends Statistic {
    override def toString = "degraded_queries_max_capacity_seconds_impacted"
  }
  val values: Seq[Statistic] = Seq(
    Star,
    SearchOperations,
    TotalSearchOperations,
    TotalSearchRequests,
    QueriesOperations,
    MultiQueriesOperations,
    AclOperations,
    TotalAclOperations,
    GetApiKeysOperations,
    GetApiKeyOperations,
    AddApiKeyOperations,
    UpdateApiKeyOperations,
    DeleteApiKeyOperations,
    ListApiKeyOperations,
    IndexingOperations,
    TotalIndexingOperations,
    BrowseOperations,
    ClearIndexOperations,
    CopyMoveOperations,
    DeleteIndexOperations,
    GetLogOperations,
    GetSettingsOperations,
    SetSettingsOperations,
    ListIndicesOperations,
    WaitTaskOperations,
    RecordOperations,
    TotalRecordsOperations,
    AddRecordOperations,
    BatchOperations,
    DeleteByQueryOperations,
    DeleteRecordOperations,
    GetRecordOperations,
    PartialUpdateRecordOperations,
    UpdateRecordOperations,
    SynonymOperations,
    TotalSynonymOperations,
    BatchSynonymOperations,
    ClearSynonymOperations,
    DeleteSynonymOperations,
    GetSynonymOperations,
    QuerySynonymOperations,
    UpdateSynonymOperations,
    RuleOperations,
    TotalRulesOperations,
    BatchRulesOperations,
    ClearRulesOperations,
    DeleteRulesOperations,
    GetRulesOperations,
    SaveRulesOperations,
    SearchRulesOperations,
    TotalRecommendRequests,
    TotalWriteOperations,
    TotalReadOperations,
    TotalOperations,
    QuerysuggestionsTotalSearchOperations,
    QuerysuggestionsTotalSearchRequests,
    QuerysuggestionsTotalAclOperations,
    QuerysuggestionsTotalIndexingOperations,
    QuerysuggestionsTotalRecordsOperations,
    QuerysuggestionsTotalSynonymOperations,
    QuerysuggestionsTotalRulesOperations,
    QuerysuggestionsTotalWriteOperations,
    QuerysuggestionsTotalReadOperations,
    QuerysuggestionsTotalOperations,
    AvgProcessingTime,
    `90pProcessingTime`,
    `99pProcessingTime`,
    QueriesAboveLastMsProcessingTime,
    Records,
    DataSize,
    FileSize,
    MaxQps,
    RegionMaxQps,
    TotalMaxQps,
    UsedSearchCapacity,
    AvgUsedSearchCapacity,
    RegionUsedSearchCapacity,
    RegionAvgUsedSearchCapacity,
    TotalUsedSearchCapacity,
    TotalAvgUsedSearchCapacity,
    DegradedQueriesSsdUsedQueriesImpacted,
    DegradedQueriesSsdUsedSecondsImpacted,
    DegradedQueriesMaxCapacityQueriesImpacted,
    DegradedQueriesMaxCapacitySecondsImpacted
  )

  def withName(name: String): Statistic = Statistic.values
    .find(_.toString == name)
    .getOrElse(throw new MappingException(s"Unknown Statistic value: $name"))
}

class StatisticSerializer
    extends CustomSerializer[Statistic](_ =>
      (
        {
          case JString(value) => Statistic.withName(value)
          case JNull          => null
        },
        { case value: Statistic =>
          JString(value.toString)
        }
      )
    )
