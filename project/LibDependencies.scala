import sbt._
import PlayCrossCompilation.dependencies
import play.sbt.PlayImport.ws
import sbt.{ModuleID, Test}

object LibDependencies {

  val playFrontendGovukVersion = "0.57.0"
  val playFrontendHmrcVersion = "0.35.0"
  val govukFrontendVersion = "3.10.1"

  lazy val libDependencies: Seq[ModuleID] = dependencies(
    shared = {
      import PlayCrossCompilation.playRevision

      val compile = Seq(
        "com.typesafe.play" %% "play"            % playRevision,
        "com.typesafe.play" %% "filters-helpers" % playRevision,
        "org.webjars.npm"   % "govuk-frontend"   % govukFrontendVersion,
        "org.joda"          % "joda-convert"     % "2.0.2",
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
    play26 = {
      val compile = Seq(
        "uk.gov.hmrc" %% "play-frontend-govuk" % s"$playFrontendGovukVersion-play-26",
        "uk.gov.hmrc" %% "play-frontend-hmrc" % s"$playFrontendHmrcVersion-play-26"
      )

      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
      ).map(_ % s"$IntegrationTest,$Test")

      compile ++ test
    },
    play27 = {
      val compile = Seq(
        "uk.gov.hmrc" %% "play-frontend-govuk" % s"$playFrontendGovukVersion-play-27",
        "uk.gov.hmrc" %% "play-frontend-hmrc" % s"$playFrontendHmrcVersion-play-27"
      )

      val test = Seq(
        "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3"
      ).map(_ % s"$IntegrationTest,$Test")

      compile ++ test
    }
  )

}
