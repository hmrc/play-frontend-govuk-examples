resolvers ++= Seq(
  Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
    Resolver.ivyStylePatterns
  ),
  MavenRepository("HMRC-open-artefacts-maven", "https://open.artefacts.tax.service.gov.uk/maven2"),
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/"
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.19")

addSbtPlugin("uk.gov.hmrc" % "sbt-auto-build" % "3.9.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.15")

addSbtPlugin("uk.gov.hmrc" % "sbt-play-cross-compilation" % "2.3.0")

addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0")
