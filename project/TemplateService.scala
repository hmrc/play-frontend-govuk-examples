import play.api.libs.json.{Json, Reads}
import scala.util.{Failure, Success, Try}
import scalaj.http.{Http, HttpOptions}

object TemplateService {

  final case class TemplateServiceResponse(name: String, html: String, nunjucks: String)

  implicit val reads: Reads[TemplateServiceResponse] = Json.reads[TemplateServiceResponse]

  def getNunjucksExamples(frontend: Frontend, component: String): Seq[TemplateServiceResponse] = {

    val endpoint: String = s"http://localhost:3000/example-usage/${frontend.templateServiceSubpath}/$component"

    val attempt: Try[Seq[TemplateServiceResponse]] = Try {
      val response = Http(endpoint)
        .option(HttpOptions.connTimeout(2000))
        .option(HttpOptions.readTimeout(5000))
        .asString
        .body

      Json.parse(response).as[Seq[TemplateServiceResponse]]
    }

    attempt match {
      case Success(njksExamples) => njksExamples
      case Failure(e) =>
        println(s"Failed to fetch Nunjucks examples for $frontend $component from Template Service at $endpoint. Details: [${e.getLocalizedMessage}].")
        Seq()
    }
  }

}