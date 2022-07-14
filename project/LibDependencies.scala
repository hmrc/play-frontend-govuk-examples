import sbt._
import PlayCrossCompilation.dependencies
import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt.{ModuleID, Test}

object LibDependencies {

  val playFrontendHmrcVersion = "3.22.0"

  lazy val libDependencies: Seq[ModuleID] = dependencies(
    shared = {
      val compile = Seq(
        "com.typesafe.play" %% "filters-helpers" % PlayVersion.current,
        "org.joda"           % "joda-convert"    % "2.0.2",
        "com.lihaoyi"       %% "fastparse"       % "2.1.2",
        "com.typesafe.play" %% "play-test"       % PlayVersion.current
      )

      val test = Seq(
        "com.vladsch.flexmark"          % "flexmark-all"     % "0.35.10",
        "org.scalatest"                %% "scalatest"        % "3.2.9",
        "org.jsoup"                     % "jsoup"            % "1.11.3",
        "org.scalacheck"               %% "scalacheck"       % "1.14.1",
        "com.googlecode.htmlcompressor" % "htmlcompressor"   % "1.5.2",
        "com.github.pathikrit"         %% "better-files"     % "3.8.0",
        "com.lihaoyi"                  %% "pprint"           % "0.5.3",
        "org.bitbucket.cowwoc"          % "diff-match-patch" % "1.2",
        ws
      ).map(_ % s"$IntegrationTest,$Test")

      compile ++ test
    },
    play28 = Seq(
      "uk.gov.hmrc"            %% "play-frontend-hmrc" % s"$playFrontendHmrcVersion-play-28",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % s"$IntegrationTest,$Test"
    )
  )

}
