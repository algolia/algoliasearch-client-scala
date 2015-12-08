package algolia

import scala.util.{Random => ScalaRandom}

trait Random {

  def shuffle(seq: Seq[String]) = ScalaRandom.shuffle(seq)

}

object Random extends Random
