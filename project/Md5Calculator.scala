import Frontend.{GovukFrontend, HmrcFrontend}
import TemplateService.{getNunjucksExamples, TemplateServiceResponse}

class Md5Calculator(exampleVersionsIterable: Iterable[ExampleForPlayVersions]) {

  private val frontendComponents = for {
    exampleVersions <- exampleVersionsIterable
    frontend = exampleVersions.frontend
    component = exampleVersions.component
  } yield (frontend, component)

  private val (govukNunjucksExamples, hmrcNunjucksExamples) =
    frontendComponents
      .toSet
      .foldLeft((Set[TemplateServiceResponse](), Set[TemplateServiceResponse]())) {
        case ((gExs, hExs), (frontend, component)) =>
          val njksComponent = frontend.toTemplateServiceExampleId(component)
          frontend match {
            case GovukFrontend => (gExs ++ getNunjucksExamples(GovukFrontend, njksComponent), hExs)
            case HmrcFrontend => (gExs, hExs ++ getNunjucksExamples(HmrcFrontend, njksComponent))
          }
      }

  def calcMd5(exampleVersions: ExampleForPlayVersions): String = {
    val id = exampleVersions.id
    val frontend = exampleVersions.frontend
    val njksId = frontend.toTemplateServiceExampleId(id)

    val nunjucksExamples = frontend match {
      case GovukFrontend => govukNunjucksExamples
      case HmrcFrontend => hmrcNunjucksExamples
    }

    nunjucksExamples
      .find(_.name.toLowerCase.replace("-", "") == njksId.toLowerCase)
      .map(_.md5)
      .getOrElse{
        println(s"ERROR: Could not find Nunjuck equivalent for frontend $frontend example named $id to calculate MD5 hash.")
        "Could not be calculated"
      }
  }

}
