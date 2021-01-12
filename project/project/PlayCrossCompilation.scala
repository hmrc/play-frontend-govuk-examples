import uk.gov.hmrc.playcrosscompilation.AbstractPlayCrossCompilation
import uk.gov.hmrc.playcrosscompilation.PlayVersion._

object PlayCrossCompilation extends AbstractPlayCrossCompilation(defaultPlayVersion = Play27) {
  val playRevision: String = PlayCrossCompilation.playVersion match {
    case Play27 => "2.7.5"
    case Play26 => "2.6.23"
    case Play25 => throw new IllegalArgumentException("This library does not support Play 2.5")
  }

  val sbtPlayCrossCompilationVersion = "0.20.0"
}
