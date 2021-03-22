import ExamplesManifestGenerator.generate
import play.sbt.PlayImport.PlayKeys._
import sys.process.Process

val libName = "play-frontend-govuk-examples"

lazy val playDir = "play-26"

lazy val IntegrationTest  = config("it") extend Test
val twirlCompileTemplates =
  TaskKey[Seq[File]]("twirl-compile-templates", "Compile twirl templates into scala source files")

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtTwirl, SbtArtifactory)
  .disablePlugins(PlayLayoutPlugin)
  .configs(IntegrationTest)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.12.10",
    crossScalaVersions := List("2.11.12", "2.12.10"),
    libraryDependencies ++= LibDependencies.libDependencies,
    resolvers :=
      Seq(
        "HMRC Releases" at "https://dl.bintray.com/hmrc/releases",
        "typesafe-releases" at "https://repo.typesafe.com/typesafe/releases/",
        "bintray" at "https://dl.bintray.com/webjars/maven"
      ),
    TwirlKeys.templateImports := templateImports,
    PlayCrossCompilation.playCrossCompilationSettings,
    makePublicallyAvailableOnBintray := true,
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "src" / "test" / playDir / "twirl",
    updateExampleSources := {
      println("==========")
      println(
        "Updating example repository sources for govuk-frontend and hmrc-frontend components to the latest versions..."
      )
      Process("git submodule update --init --recursive") #&& Process("git submodule update --remote")
      println("Task completed")
    },
    generateExamplesManifest := {
      println("==========")
      val _                      = generateExamples.value
      println("Generating manifest.json...")
      val manifestFile           = (resourceDirectory in Test).value / "manifest.json"
      val examplesDir: File      = baseDirectory.value / "src/test"
      val allExamples: Set[File] = (examplesDir ** "*.scala.html").get.toSet
      generate(allExamples = allExamples, manifestFile = manifestFile)
      println("Task completed")
      manifestFile
    },
    parallelExecution in sbt.Test := false,
    playMonitoredFiles ++= (sourceDirectories in (Compile, TwirlKeys.compileTemplates)).value,
    unmanagedResourceDirectories in Test ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      "playVersion" -> PlayCrossCompilation.playVersion,
      sources in (Compile, TwirlKeys.compileTemplates)
    ),
    run := {
      val _ = generateExamplesManifest.value
    },
    scalacOptions += "-verbose",
    fork in Test := false
  )
  .settings(inConfig(IntegrationTest)(itSettings): _*)
  .settings(
    // The following line undoes the inclusion of Twirl templates in the sbt-header sources
    // enabled by sbt-auto-build. These are generated example templates that we don't want to
    // add licence headers to because it is generated example code we are explicitly inviting
    // users to copy and paste.
    //
    // The mechanism documented here: https://github.com/sbt/sbt-header/tree/v4.1.0#excluding-files
    // does not work for Twirl templates and even if it did work this issue would
    // prevent the templates from being compiled: https://github.com/sbt/sbt-header/issues/130
    unmanagedSources.in(Compile, headerCreate) := sources.in(Compile, unmanagedSources).value
  )

lazy val itSettings = Defaults.itSettings :+ (unmanagedSourceDirectories += sourceDirectory.value / playDir)

lazy val templateImports: Seq[String] = Seq(
  "_root_.play.twirl.api.Html",
  "_root_.play.twirl.api.HtmlFormat",
  "_root_.play.twirl.api.JavaScript",
  "_root_.play.twirl.api.Txt",
  "_root_.play.twirl.api.Xml",
  "play.api.mvc._",
  "play.api.data._",
  "play.api.i18n._",
  "play.api.templates.PlayMagic._",
  "uk.gov.hmrc.govukfrontend.views.html.components.implicits._",
  "_root_.play.twirl.api.TwirlHelperImports._"
)

/**
  * Updates source repositories for examples for govuk-frontend and hmrc-frontend
  * respectively.
  *
  * Run this task in the sbt console via <code>updateExampleSources</code>.
  */
lazy val updateExampleSources = taskKey[Unit]("Update source example repositories for govuk-frontend and hmrc-frontend")

/**
  * Generates Twirl examples for govuk-frontend and hmrc-frontend components under
  * src/test/play-XX/twirl/uk/gov/hmrc/govukfrontend/views/examples and
  * src/test/play-XX/twirl/uk/gov/hmrc/hmrcfrontend/views/examples appropriately,
  * where XX designates the relevant Play versions. Currently implemented for
  * Play 2.6 and Play 2.7.
  *
  * Run this task in the sbt console via <code>generateExamples</code>.
  */
lazy val generateExamples = taskKey[Unit]("Generate Twirl examples")
fullRunTask(
  generateExamples,
  Compile,
  "uk.gov.hmrc.govukfrontend.examples.ExampleGenerator"
)

/**
  * Generates the manifest.json file in the [[src/test/resources]] folder, used by the Design System browser
  * extension to display Twirl examples for the library's components.
  *
  * The generation of all Play Twirl examples is a prerequisite to this task.
  * generateExamplesTask is hence executed when this task is run.
  * Run this task in the sbt console via <code>generateExamples</code>.
  */
lazy val generateExamplesManifest = taskKey[File]("Generate Twirl examples manifest.json file")
