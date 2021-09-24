/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package algolia.dsl

import algolia.AlgoliaDsl.ABTests
import algolia.definitions._
import algolia.objects.{
  FrequentlyBoughtTogetherQuery,
  RecommendationsQuery,
  RelatedProductsQuery,
  Strategy
}
import algolia.responses._
import algolia.{AlgoliaClient, Executable}
import org.json4s.Formats
import org.json4s.JsonAST.JObject

import scala.concurrent.{ExecutionContext, Future}

trait GetDsl {

  implicit val formats: Formats

  case object get {

    def objectId(objectId: String) = GetObjectDefinition(oid = Some(objectId))

    def from(index: String) = GetObjectDefinition(index = Some(index))

    def key(keyName: String) = GetKeyDefinition(keyName)

    @deprecated("use list keys", "1.27.1")
    def allKeys() = ListKeysDefinition()

    @deprecated("use list keysFrom", "1.27.1")
    def allKeysFrom(indexName: String) =
      ListKeysDefinition(indexName = Some(indexName))

    def synonym(synId: String) = GetSynonymDefinition(synId = synId)

    def rule(ruleId: String) = GetRuleDefinition(objectId = ruleId)

    // AB test
    def abTest(id: Int) = GetABTestDefinition(id)

    def all(abTests: ABTests) = GetABTestsDefinition()

    def topUserID = GetTopUserIDDefinition()

    def userID(userID: String) = GetUserIDDefinition(userID)

    // Personalization
    def strategy(): GeStrategyDefinition = GeStrategyDefinition()

    @deprecated(
      "Method is deprecated, please use strategy methods instead",
      "1.34"
    )
    def personalizationStrategy() = GetPersonalizationStrategyDefinition()

    @deprecated(
      "Method is deprecated, please use strategy methods instead",
      "1.40.0"
    )
    def personalizationRecommendationStrategy() =
      GetRecommendationStrategyDefinition()

    def personalizationProfile(userToken: String) =
      GetPersonalizationProfileDefinition(userToken)

    def dictionarySettings: GetSettingsDictionaryDefinition =
      GetSettingsDictionaryDefinition()

    def recommendation(
        query: RecommendationsQuery
    ): GetRecommendationDefinition = GetRecommendationDefinition(query)

    def relatedProduct(
        query: RelatedProductsQuery
    ): GetRelatedProductsDefinition = GetRelatedProductsDefinition(query)

    def frequentlyBoughtTogether(
        query: FrequentlyBoughtTogetherQuery
    ): GetFrequentlyBoughtTogetherDefinition =
      GetFrequentlyBoughtTogetherDefinition(query)
  }

  implicit object GetObjectDefinitionExecutable
      extends Executable[GetObjectDefinition, GetObject] {

    override def apply(client: AlgoliaClient, query: GetObjectDefinition)(
        implicit executor: ExecutionContext
    ): Future[GetObject] = {
      client.request[JObject](query.build()).map(GetObject)
    }

  }

  implicit object GetObjectsDefinitionExecutable
      extends Executable[GetObjectsDefinition, Results] {

    override def apply(client: AlgoliaClient, query: GetObjectsDefinition)(
        implicit executor: ExecutionContext
    ): Future[Results] = {
      client.request[Results](query.build())
    }

  }

  implicit object GetTopUserIDExecutable
      extends Executable[GetTopUserIDDefinition, TopUserID] {
    override def apply(client: AlgoliaClient, query: GetTopUserIDDefinition)(
        implicit executor: ExecutionContext
    ): Future[TopUserID] = {
      client.request[TopUserID](query.build())
    }
  }

  implicit object GetUserIDExecutable
      extends Executable[GetUserIDDefinition, UserDataWithCluster] {
    override def apply(client: AlgoliaClient, query: GetUserIDDefinition)(
        implicit executor: ExecutionContext
    ): Future[UserDataWithCluster] = {
      client.request[UserDataWithCluster](query.build())
    }
  }

  implicit object GetStrategyExecutable
      extends Executable[GeStrategyDefinition, GetStrategyResponse] {
    override def apply(client: AlgoliaClient, query: GeStrategyDefinition)(
        implicit executor: ExecutionContext
    ): Future[GetStrategyResponse] = {
      client.request[GetStrategyResponse](query.build())
    }
  }

  @deprecated(
    "Method is deprecated, please use strategy methods instead",
    "1.34"
  )
  implicit object GetPersonalizationStrategyExecutable
      extends Executable[GetPersonalizationStrategyDefinition, Strategy] {
    override def apply(
        client: AlgoliaClient,
        query: GetPersonalizationStrategyDefinition
    )(implicit executor: ExecutionContext): Future[Strategy] = {
      client.request[Strategy](query.build())
    }
  }

  @deprecated(
    "Method is deprecated, please use strategy methods instead",
    "1.40.0"
  )
  implicit object GetPersonalizationRecommendationStrategy
      extends Executable[
        GetRecommendationStrategyDefinition,
        GetStrategyResponse
      ] {
    override def apply(
        client: AlgoliaClient,
        query: GetRecommendationStrategyDefinition
    )(implicit executor: ExecutionContext): Future[GetStrategyResponse] = {
      client.request[GetStrategyResponse](query.build())
    }
  }

  implicit object GetPersonalizationProfileExecutable
      extends Executable[
        GetPersonalizationProfileDefinition,
        PersonalizationProfileResponse
      ] {
    override def apply(
        client: AlgoliaClient,
        query: GetPersonalizationProfileDefinition
    )(
        implicit executor: ExecutionContext
    ): Future[PersonalizationProfileResponse] = {
      client.request[PersonalizationProfileResponse](query.build())
    }
  }
}
