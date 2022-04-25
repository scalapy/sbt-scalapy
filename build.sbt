import Dependencies._

inThisBuild(
  List(
    organization := "ai.kien",
    homepage := Some(url("https://github.com/kiendang/sbt-scalapy")),
    licenses := List(
      "BSD-3-Clause" -> url("https://opensource.org/licenses/BSD-3-Clause")
    ),
    developers := List(
      Developer(
        "kiendang",
        "Dang Trung Kien",
        "mail@kien.ai",
        url("https://kien.ai")
      )
    )
  )
)

ThisBuild / scalafixDependencies += organizeImports

lazy val commonSettings = Seq(
  pluginCrossBuild / sbtVersion := "1.2.1",
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalacOptions += "-Ywarn-unused-import"
)

lazy val sbtPluginSettings = Seq(
  libraryDependencies += pythonNativeLibs
)

lazy val scriptedSettings = Seq(
  scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
  scriptedLaunchOpts ++= {
    for {
      virtualenv <- Option(System.getenv("CI_VIRTUALENV"))
      python <- Option(System.getenv("CI_PYTHON"))
    } yield s"-Dplugin.python.executable=$python"
  }.toSeq,
  scriptedBufferLog := false,
  scripted := scripted
    .dependsOn(
      `sbt-scalapy` / publishLocal,
      `sbt-scalapy-native` / publishLocal
    )
    .evaluated
)

lazy val noPublishSettings = Seq(
  publishArtifact := false,
  packagedArtifacts := Map.empty,
  publish := {},
  publishLocal := {}
)

lazy val `sbt-scalapy` = project
  .in(file("sbt-scalapy"))
  .enablePlugins(SbtPlugin)
  .settings(commonSettings)
  .settings(sbtPluginSettings)
  .settings(
    name := "sbt-scalapy",
    description := "a plugin for automatically configuration of SBT for ScalaPy projects"
  )

lazy val `sbt-scalapy-native` = project
  .in(file("sbt-scalapy-native"))
  .enablePlugins(SbtPlugin)
  .settings(commonSettings)
  .settings(sbtPluginSettings)
  .settings(
    name := "sbt-scalapy-native",
    description := "a plugin for automatically configuration of SBT for native ScalaPy projects",
    addSbtPlugin(sbtScalaNative)
  )
  .dependsOn(`sbt-scalapy`)

lazy val `sbt-scalapy-test` = project
  .in(file("sbt-scalapy-test"))
  .enablePlugins(SbtPlugin)
  .settings(commonSettings)
  .settings(noPublishSettings)
  .settings(scriptedSettings)
