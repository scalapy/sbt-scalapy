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

lazy val `sbt-scalapy` = project
  .in(file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-scalapy",
    description := "a plugin for automatically configuration of SBT for ScalaPy projects",
    libraryDependencies += pythonNativeLibs,
    resolvers +=
      "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots"
  )
  .settings(
    pluginCrossBuild / sbtVersion := "1.2.1",
    scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
    scriptedLaunchOpts ++= {
      for {
        virtualenv <- Option(System.getenv("CI_VIRTUALENV"))
        python <- Option(System.getenv("CI_PYTHON"))
      } yield s"-Dplugin.python.executable=$python"
    }.toSeq,
    scriptedBufferLog := false,
    addSbtPlugin(sbtScalaNative)
  )
  .settings(
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Ywarn-unused-import"
  )
