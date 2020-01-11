import java.io.File
import Frontend.{GovukFrontend, HmrcFrontend}

case class ExampleVersions(component: String, name: String, frontend: Frontend, playVersionedSources: Map[Int, File]) {
  val id = s"${frontend.exampleIdPrefix}$component/$name"
}

object ExampleVersions {

  private val exampleReg = """.*/play-(\d*)/(?:.*/){4}(.*)/(?:.*/){2}(.*)/(.*).scala.html$""".r

  def toExampleVersionsIterable(files: Iterable[File]): Iterable[ExampleVersions] = {

    case class Example(playVersion: Int, frontend: Frontend, component: String, name: String, source: File)

    val commonalities: Iterable[Example] = for {
      file <- files
    } yield file.getAbsolutePath match {
      case path@exampleReg(playVersion, rawFrontend, component, name) =>
        val frontend = rawFrontend match {
          case GovukFrontend.twirlSubpath => GovukFrontend
          case HmrcFrontend.twirlSubpath => HmrcFrontend
          case _ => throw new Exception(s"Could not identify frontend for example with path: $path.")
        }
        Example(playVersion.toInt, frontend, component, name, file)
      case path => throw new Exception(s"Could not identify play version, component and example name for example files with path in the format of the following: $path.")
    }

    val rawExampleVersionsIterable: Iterable[Iterable[Example]] =
      commonalities.groupBy(c => (c.frontend, c.component, c.name)).values

    for {
      versions <- rawExampleVersionsIterable
    } yield ExampleVersions(
      component            = versions.head.component,
      name                 = versions.head.name,
      frontend             = versions.head.frontend,
      playVersionedSources = versions.map(v => v.playVersion -> v.source).toMap
    )
  }

}
