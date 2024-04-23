import play.api.libs.json.Json
import sbt._
import ExampleForPlayVersions.toExamplesForPlayVersions

object ExamplesManifestGenerator {

  /**
    * sbt task implementation that generates a <code>manifest.json</code> with references to the example files which can be used by the
    * Govuk Design System browser extension [[https://github.com/hmrc/play-frontend-govuk-extension]]
    *
    * It collects all the examples in the folder [[src/test/play-3/twirl/uk/gov/hmrc/govukfrontend/views/examples]]
    * and generates a <code>manifest.json</code> file in the folder
    * [[src/test/resources]]which contains the location of each play-3 example
    * provided by x-govuk-component-renderer.
    *
    * To exclude an example from the manifest include a Twirl comment with the following contents
    * <code>@*exclude-from-manifest*@</code> in the template code.
    *
    * x-govuk-component-renderer [[https://github.com/hmrc/x-govuk-component-renderer]] must be running for the task to succeed,
    * as it reads from the live Govuk Design System [[https://design-system.service.gov.uk/components/]] to provide the
    * examples.
    *
    * @param allExamples
    * @param manifestFile
    * @return
    */
  def generate(allExamples: Set[File], manifestFile: File): Set[File] = {

    def removeExcludes(examples: Set[File]): Set[File] = {
      val excludesRegex = """@\*exclude-from-manifest\*@"""
      examples.filterNot(example => excludesRegex.r.findFirstMatchIn(IO.read(example)).nonEmpty)
    }

    println(s">>>>>>>>>>>>>>>> Generating manifest examples for ${allExamples.size} files")
    removeExcludes(allExamples).toList match {
      case Nil      => Set.empty
      case examples =>
        val content = manifestContent(examples)
        IO.write(content = content, file = manifestFile, append = false)
        Set(manifestFile)
    }
  }

  /**
    * Generates the contents of the <code>manifest.json</code> with references to the example files which can be used by the
    * * Govuk Design System plugin [[https://github.com/hmrc/play-frontend-govuk-extension]]
    *
    * @param allExamples
    * @return
    */
  private def manifestContent(allExamples: List[File]): String = {

    val exampleVersionsIterable: Iterable[ExampleForPlayVersions] = toExamplesForPlayVersions(allExamples)

    val uriReg = """.*/(play-\d*/(?:.*/){8}.*\.scala\.html)$""".r

    def toManifestElem(exampleVersions: ExampleForPlayVersions): ManifestJson = {
      val exampleId = exampleVersions.id

      val playRefs = exampleVersions.playVersionedSources.map { case (playVersion, source) =>
        source.getAbsolutePath match {
          case uriReg(uri) =>
            PlayVersionedExample(
              playVersion = playVersion,
              ref = ExampleRef(uri = uri)
            )
          case path        =>
            throw new Exception(
              s"Failed to abstract manifest URI for example ${exampleVersions.frontend} $exampleId at path $path."
            )
        }
      }.toSeq

      ManifestJson(
        id = exampleId,
        versions = Versions(playVersionedExamples = playRefs)
      )
    }

    val manifestElems = exampleVersionsIterable.map(toManifestElem).toSeq.sortBy(_.id)
    val json          = Json.toJson(manifestElems)
    Json.prettyPrint(json)
  }

}
