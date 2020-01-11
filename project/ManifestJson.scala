import play.api.libs.json.{JsObject, Json, OWrites}

final case class ManifestJson(
  id: String,
  versions: Versions
)

object ManifestJson {
  implicit val writes: OWrites[ManifestJson] = Json.writes[ManifestJson]
}

final case class Versions(
  playRefs: Seq[PlayExampleRef]
)

object Versions {
  implicit val writes: OWrites[Versions] = new OWrites[Versions] {
    override def writes(o: Versions): JsObject = JsObject(
      o.playRefs
        .sortBy(_.playVersion)
        .map(playRef => s"play${playRef.playVersion}" -> Json.toJsObject(playRef.ref))
        .toMap
    )
  }
}

final case class PlayExampleRef(
 playVersion: Int,
 ref: ExampleRef
)

final case class ExampleRef(
  uri: String,
  htmlChecksum: String
)

object ExampleRef {
  implicit val writes: OWrites[ExampleRef] = Json.writes[ExampleRef]
}
