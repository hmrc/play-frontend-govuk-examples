import uk.gov.hmrc.DefaultBuildSettings
import ExamplesManifestGenerator.generate
import play.sbt.PlayImport.PlayKeys._
import sys.process.Process

val libName = "play-frontend-govuk-examples"

lazy val playDir = "play-3"

val twirlCompileTemplates =
  TaskKey[Seq[File]]("twirl-compile-templates", "Compile twirl templates into scala source files")

lazy val sharedSettings = Seq(
  libraryDependencies ++= LibDependencies.libDependencies,
  majorVersion := 0,
  scalaVersion := "2.13.16"
)

lazy val root = Project(libName, file("."))
  .enablePlugins(PlayScala, SbtTwirl)
  .disablePlugins(PlayLayoutPlugin)
  .settings(
    name := libName,
    majorVersion := 0,
    scalaVersion := "2.13.12",
    sharedSettings,
    resolvers += "HMRC-open-artefacts-maven" at "https://open.artefacts.tax.service.gov.uk/maven2",
    resolvers += Resolver.url("HMRC-open-artefacts-ivy", url("https://open.artefacts.tax.service.gov.uk/ivy2"))(
      Resolver.ivyStylePatterns
    ),
    TwirlKeys.templateImports := templateImports,
    isPublicArtefact := true,
    Compile / TwirlKeys.compileTemplates / sourceDirectories +=
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
      println("Generating manifest.json...")
      val manifestFile           = (Test / resourceDirectory).value / "manifest.json"
      val examplesDir: File      = baseDirectory.value / "src/test"
      val allExamples: Set[File] = (examplesDir ** "*.scala.html").get.toSet
      generate(allExamples = allExamples, manifestFile = manifestFile)
      println("Task completed")
      manifestFile
    },
    sbt.Test / parallelExecution := false,
    playMonitoredFiles ++= (Compile / TwirlKeys.compileTemplates / sourceDirectories).value,
    Test / unmanagedResourceDirectories ++= Seq(baseDirectory(_ / "target/web/public/test").value),
    buildInfoKeys ++= Seq[BuildInfoKey](
      Compile / TwirlKeys.compileTemplates / sources
    ),
    run := {
      val _ = generateExamplesManifest.value
    },
    scalacOptions += "-verbose",
    Test / fork := false
  )
  .settings(
    // The following line removes the Twirl templates from the sbt-header sources
    // enabled by sbt-auto-build. These are generated example templates that we don't want to
    // add licence headers to because it is generated example code we are explicitly inviting
    // users to copy and paste. Updated to work with sbt-auto-build ^3.5.0.
    Compile / headerSources --= (Compile / twirlCompileTemplates / sources).value
  )

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
  * Play 3.0
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

lazy val it = project
  .enablePlugins(PlayScala)
  .dependsOn(root % "test->test")
  .settings(DefaultBuildSettings.itSettings())
  .settings(sharedSettings)
