import java.io.File
import Frontend.{GovukFrontend, HmrcFrontend}

case class ExampleForPlayVersions(
  component: String,
  name: String,
  frontend: Frontend,
  playVersionedSources: Map[Int, File]
) {
  val id = s"${frontend.exampleIdPrefix}$component/$name"
}

object ExampleForPlayVersions {

  def toExamplesForPlayVersions(files: Iterable[File]): Iterable[ExampleForPlayVersions] = {

    val examples: Iterable[Example] = files.map(toExample)

    val playExamplesGroupedByExampleType: Iterable[Iterable[Example]] = examples
      .groupBy(c => (c.frontend, c.component, c.name))
      .values

    for {
      exampleInAllPlayVersions <- playExamplesGroupedByExampleType
    } yield ExampleForPlayVersions(
      component = exampleInAllPlayVersions.head.component,
      name = exampleInAllPlayVersions.head.name,
      frontend = exampleInAllPlayVersions.head.frontend,
      playVersionedSources = exampleInAllPlayVersions.map(v => v.playVersion -> v.source).toMap
    )
  }

  private val exampleReg = """.*/play-(\d*)/(?:.*/){4}(.*)/(?:.*/){2}(.*)/(.*).scala.html$""".r

  private case class Example(playVersion: Int, frontend: Frontend, component: String, name: String, source: File)

  private def toExample(file: File): Example = file.getAbsolutePath match {
    case path @ exampleReg(playVersion, rawFrontend, component, name) =>
      val frontend = rawFrontend match {
        case GovukFrontend.twirlSubpath => GovukFrontend
        case HmrcFrontend.twirlSubpath  => HmrcFrontend
        case _                          => throw new Exception(s"Could not identify frontend for example with path: $path.")
      }
      Example(playVersion.toInt, frontend, component, name, file)
    case path                                                         =>
      throw new Exception(
        s"Could not identify play version, component and example name for example files with path in the format of the following: $path."
      )
  }

}
