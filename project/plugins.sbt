resolvers ++= Seq(
  Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
    Resolver.ivyStylePatterns
  ),
  MavenRepository("HMRC-open-artefacts-maven", "https://open.artefacts.tax.service.gov.uk/maven2"),
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.2")

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.21.0")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
