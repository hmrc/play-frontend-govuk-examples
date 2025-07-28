import play.sbt.PlayImport.ws
import sbt.*

object LibDependencies {

  val playFrontendHmrcVersion = "12.8.0"

  def libDependencies: Seq[ModuleID] = compile ++ test

  private lazy val compile = Seq(
    // Was removed from core Java from v15 onwards, used by NunjucksParser
    "org.openjdk.nashorn" % "nashorn-core"               % "15.6",
    "org.playframework"  %% "play-filters-helpers"       % "3.0.0",
    "org.joda"            % "joda-convert"               % "2.0.2",
    "com.lihaoyi"        %% "fastparse"                  % "2.1.3",
    "org.playframework"  %% "play-test"                  % "3.0.0",
    "uk.gov.hmrc"        %% "play-frontend-hmrc-play-30" % s"$playFrontendHmrcVersion"
  )

  private lazy val test = Seq(
    "com.vladsch.flexmark"          % "flexmark-all"       % "0.64.8",
    "org.scalatest"                %% "scalatest"          % "3.2.9",
    "org.jsoup"                     % "jsoup"              % "1.11.3",
    "org.scalacheck"               %% "scalacheck"         % "1.14.1",
    "com.googlecode.htmlcompressor" % "htmlcompressor"     % "1.5.2",
    "com.github.pathikrit"         %% "better-files"       % "3.8.0",
    "com.lihaoyi"                  %% "pprint"             % "0.5.5",
    "org.bitbucket.cowwoc"          % "diff-match-patch"   % "1.2",
    "org.scalatestplus.play"       %% "scalatestplus-play" % "7.0.0",
    ws
  ).map(_ % s"$Test")

}
