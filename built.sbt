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

lazy val sbtPluginSettings = Def.settings(
  pluginCrossBuild / sbtVersion := "1.2.1",
  libraryDependencies += pythonNativeLibs,
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  scalacOptions += "-Ywarn-unused-import",
  scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
  scriptedBufferLog := false
)

lazy val `sbt-scalapy` = project
  .in(file("sbt-scalapy"))
  .enablePlugins(SbtPlugin)
  .settings(sbtPluginSettings)
  .settings(
    name := "sbt-scalapy",
    description := "a plugin for automatically configuration of SBT for ScalaPy projects"
  )

lazy val `sbt-scalapy-native` = project
  .in(file("sbt-scalapy-native"))
  .enablePlugins(SbtPlugin)
  .settings(sbtPluginSettings)
  .settings(
    name := "sbt-scalapy-native",
    description := "a plugin for automatically configuration of SBT for native ScalaPy projects",
    addSbtPlugin(sbtScalaNative)
  )
  .dependsOn(`sbt-scalapy`)
