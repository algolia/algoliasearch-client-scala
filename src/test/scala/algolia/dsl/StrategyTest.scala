package algolia.dsl

import algolia.AlgoliaDsl._
import algolia.AlgoliaTest
import algolia.http.{GET, HttpPayload, POST}
import algolia.objects.{Score, ScoreType, Strategy}

import scala.language.postfixOps

class StrategyTest extends AlgoliaTest {

  describe("personalization strategy payloads") {

    it("should produce empty set strategy payload") {
      (set personalizationStrategy Strategy()).build() should be(
        HttpPayload(
          POST,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = Some("{}"),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should produce non-empty set strategy payload") {
      val strategy = Strategy(
        eventsScoring = Some(
          Map(
            "event 1" -> ScoreType(10, "click"),
            "event 2" -> ScoreType(20, "conversion")
          )),
        facetsScoring = Some(
          Map(
            "event 3" -> Score(30)
          ))
      )

      (set personalizationStrategy strategy).build() should be(
        HttpPayload(
          POST,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = Some(
            """{"eventsScoring":{"event 1":{"score":10,"type":"click"},"event 2":{"score":20,"type":"conversion"}},"facetsScoring":{"event 3":{"score":30}}}"""),
          isSearch = false,
          requestOptions = None
        )
      )
    }

    it("should produce get strategy payload") {
      (get personalizationStrategy).build() should be(
        HttpPayload(
          GET,
          Seq("1", "recommendation", "personalization", "strategy"),
          body = None,
          isSearch = true,
          requestOptions = None
        )
      )
    }

  }
}
