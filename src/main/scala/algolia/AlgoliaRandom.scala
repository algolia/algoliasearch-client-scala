package algolia

import scala.util.{Random => ScalaRandom}

private[algolia] class AlgoliaRandom {

  def shuffle(seq: Seq[String]) = ScalaRandom.shuffle(seq)

}

private[algolia] object AlgoliaRandom extends AlgoliaRandom
