import ExamplesManifestGenerator.generate
import PlayCrossCompilation.{dependencies, playVersion}
import play.sbt.PlayImport.PlayKeys._
import sbt.classpath.ClasspathUtilities
import uk.gov.hmrc.playcrosscompilation.PlayVersion.{Play25, Play26}

val libName = "play-frontend-govuk-examples"

lazy val playDir =
  (if (PlayCrossCompilation.playVersion == Play25) "play-25"
   else "play-26")

lazy val IntegrationTest = config("it") extend Test

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.11.12",
    crossScalaVersions := List("2.11.12", "2.12.8"),
    libraryDependencies ++= libDependencies,
    dependencyOverrides ++= overrides,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/",
        "bintray" at "https://dl.bintray.com/webjars/maven"
      ),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    unmanagedSourceDirectories in Compile += baseDirectory.value / "src/test/twirl",
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "test" / playDir / "twirl",
    generateExamples := {
      val classpath = (fullClasspath in Runtime).value
      val loader: ClassLoader = ClasspathUtilities.toLoader(classpath.map(_.data).map(_.getAbsoluteFile))
      loader.loadClass("uk.gov.hmrc.govukfrontend.examples.ExampleGenerator").newInstance()
    },
    generateExamplesManifest := {
      val _ = generateExamples.value
      println("Generating manifest.json")
      val manifestFile = (resourceDirectory in Test).value / "manifest.json"
      val examplesDir: File = baseDirectory.value / "src/test"
      val allExamples: Set[File] = (examplesDir ** "*.scala.html").get.toSet
      generate(allExamples = allExamples, manifestFile = manifestFile)
      manifestFile
    },
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    routesGenerator := {
      if (playVersion == Play25) StaticRoutesGenerator
      else InjectedRoutesGenerator
    },
    unmanagedResourceDirectories in Test ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion" -> PlayCrossCompilation.playVersion,
      sources in (Compile, TwirlKeys.compileTemplates)
    ),
    run := {
      val _ = generateExamplesManifest.value
    }
  )
  .settings(inConfig(IntegrationTest)(itSettings): _*)

lazy val itSettings = Defaults.itSettings :+ (unmanagedSourceDirectories += sourceDirectory.value / playDir)

lazy val libDependencies: Seq[ModuleID] = dependencies(
  shared = {
    import PlayCrossCompilation.playRevision

    val compile = Seq(
      "com.typesafe.play" %% "play"            % playRevision,
      "com.typesafe.play" %% "filters-helpers" % playRevision,
      "org.joda"          % "joda-convert"     % "2.0.2",
      "org.webjars.npm"   % "govuk-frontend"   % "3.3.0",
      "com.lihaoyi"       %% "fastparse"       % "2.1.2"
    )

    val test = Seq(
      "org.scalatest"                 %% "scalatest"       % "3.0.8",
      "org.pegdown"                   % "pegdown"          % "1.6.0",
      "org.jsoup"                     % "jsoup"            % "1.11.3",
      "com.typesafe.play"             %% "play-test"       % playRevision,
      "org.scalacheck"                %% "scalacheck"      % "1.14.1",
      "com.googlecode.htmlcompressor" % "htmlcompressor"   % "1.5.2",
      "com.github.pathikrit"          %% "better-files"    % "3.8.0",
      "com.lihaoyi"                   %% "pprint"          % "0.5.3",
      "org.bitbucket.cowwoc"          % "diff-match-patch" % "1.2",
      ws
    ).map(_ % s"$IntegrationTest,$Test")

    compile ++ test
  },
  play25 = {
    val compile = Seq(
      "uk.gov.hmrc" %% "play-frontend-govuk" % "0.32.0-play-25",
      "uk.gov.hmrc" %% "play-frontend-hmrc" % "0.10.0-play-25"
    )

    val test = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1"
    ).map(_ % s"$IntegrationTest,$Test")

    compile ++ test
  },
  play26 = {
    val compile = Seq(
      "uk.gov.hmrc" %% "play-frontend-govuk" % "0.32.0-play-26",
      "uk.gov.hmrc" %% "play-frontend-hmrc" % "0.10.0-play-26"
    )

    val test = Seq(
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
    ).map(_ % s"$IntegrationTest,$Test")

    compile ++ test
  }
)

lazy val overrides: Set[ModuleID] = dependencies(
  play25 = Seq(
    "com.typesafe.play" %% "twirl-api" % "1.1.1"
  )
).toSet

lazy val templateImports: Seq[String] = {

  val allImports = Seq(
    "_root_.play.twirl.api.Html",
    "_root_.play.twirl.api.HtmlFormat",
    "_root_.play.twirl.api.JavaScript",
    "_root_.play.twirl.api.Txt",
    "_root_.play.twirl.api.Xml",
    "play.api.mvc._",
    "play.api.data._",
    "play.api.i18n._",
    "play.api.templates.PlayMagic._",
    "uk.gov.hmrc.govukfrontend.views.html.components.implicits._"
  )

  val specificImports = PlayCrossCompilation.playVersion match {
    case Play25 =>
      Seq(
        "_root_.play.twirl.api.TemplateMagic._"
      )
    case Play26 =>
      Seq(
        "_root_.play.twirl.api.TwirlFeatureImports._",
        "_root_.play.twirl.api.TwirlHelperImports._"
      )
  }

  allImports ++ specificImports
}

/**
  * Generates Twirl examples for govuk-frontend and hmrc-frontend components under
  * src/test/play-XX/twirl/uk/gov/hmrc/govukfrontend/views/examples and
  * src/test/play-XX/twirl/uk/gov/hmrc/hmrcfrontend/views/examples appropriately,
  * where XX designates the relevant Play versions. Currently implement for
  * Play 2.5 and Play 2.6.
  *
  * Run this task in the sbt console via <code>generateExamplesTask</code>.
  */
lazy val generateExamples = taskKey[Any]("Generate Twirl examples")

/**
  * Generates the manifest.json file in the [[src/test/resources]] folder, used by the Design System browser
  * extension to display Twirl examples for the library's components.
  *
  * The generation of all Play Twirl examples is a prerequisite to this task.
  * generateExamplesTask is hence executed when this task is run.
  * Run this task in the sbt console via <code>generateExamplesTask</code>.
  */
lazy val generateExamplesManifest = taskKey[File]("Generate Twirl examples manifest.json file")
