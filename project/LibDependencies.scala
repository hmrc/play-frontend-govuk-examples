import play.sbt.PlayImport.ws
import sbt.*

object LibDependencies {

  private val playFrontendHmrcVersion = "13.3.0"
  private val playVersion             = "3.0.10"

  def libDependencies: Seq[ModuleID] = compile ++ test

  private lazy val compile = Seq(
    // Was removed from core Java from v15 onwards, used by NunjucksParser
    "org.openjdk.nashorn" % "nashorn-core"               % "15.7",
    "org.playframework"  %% "play-filters-helpers"       % playVersion,
    "com.lihaoyi"        %% "fastparse"                  % "2.3.1",
    "org.playframework"  %% "play-test"                  % playVersion,
    "uk.gov.hmrc"        %% "play-frontend-hmrc-play-30" % playFrontendHmrcVersion
  )

  private lazy val test = Seq(
    "com.vladsch.flexmark"          % "flexmark-all"       % "0.64.8",
    "org.scalatest"                %% "scalatest"          % "3.2.19",
    "org.jsoup"                     % "jsoup"              % "1.22.1",
    "org.scalacheck"               %% "scalacheck"         % "1.19.0",
    "com.googlecode.htmlcompressor" % "htmlcompressor"     % "1.5.2",
    "com.github.pathikrit"         %% "better-files"       % "3.9.2",
    "com.lihaoyi"                  %% "pprint"             % "0.9.6",
    "org.bitbucket.cowwoc"          % "diff-match-patch"   % "1.2",
    "org.scalatestplus.play"       %% "scalatestplus-play" % "7.0.0",
    ws
  ).map(_ % s"$Test")

}
