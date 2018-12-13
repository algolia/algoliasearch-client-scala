package algolia.objects

case class Strategy(eventsScoring: Option[Map[String, ScoreType]] = None,
                    facetsScoring: Option[Map[String, Score]] = None)

case class ScoreType(score: Int, `type`: String)

case class Score(score: Int)
