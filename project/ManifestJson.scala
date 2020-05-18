import play.api.libs.json.{JsObject, Json, OWrites}

final case class ManifestJson(
  id: String,
  versions: Versions
)

object ManifestJson {
  implicit val writes: OWrites[ManifestJson] = Json.writes[ManifestJson]
}

final case class Versions(
  playVersionedExamples: Seq[PlayVersionedExample]
)

object Versions {
  implicit val writes: OWrites[Versions] = new OWrites[Versions] {
    override def writes(o: Versions): JsObject = JsObject(
      o.playVersionedExamples
        .sortBy(_.playVersion)
        .map { example =>
          s"play${example.playVersion}" -> Json.toJsObject(example.ref)
        }
        .toMap
    )
  }
}

final case class PlayVersionedExample(
  playVersion: Int,
  ref: ExampleRef
)

final case class ExampleRef(
  uri: String
)

object ExampleRef {
  implicit val writes: OWrites[ExampleRef] = Json.writes[ExampleRef]
}
