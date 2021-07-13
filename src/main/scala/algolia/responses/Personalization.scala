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

package algolia.responses

import algolia.objects.{EventsScoring, FacetsScoring}

case class SetStrategyResponse(status: Int, message: String)

case class GetStrategyResponse(
    eventsScoring: Option[Seq[EventsScoring]],
    facetsScoring: Option[Seq[FacetsScoring]],
    personalizationImpact: Option[Int]
)

case class SetStrategyResult(updatedAt: String)

/**
  * User profile built from Personalization strategy.
  *
  * @param userToken the user token representing the user and associated data
  * @param lastEventAt the last processed event timestamp using the ISO 8601 format.
  * @param scores The profile is structured by facet name used in the strategy. Each facet value is mapped to its score.
  *               Each score represents the user affinity for a specific facet value given the userToken past events and
  *               the Personalization strategy defined. Scores are bounded to 20.
  */
case class PersonalizationProfileResponse(
    userToken: String,
    lastEventAt: String,
    scores: Map[String, Any]
)

/**
  * Delete the user profile response.
  *
  * @param userToken the user token representing the user and associated data
  * @param deletedUntil date until which the data can safely be considered as deleted for the given use
  */
case class DeletePersonalizationProfileResponse(
    userToken: String,
    deletedUntil: String
)
