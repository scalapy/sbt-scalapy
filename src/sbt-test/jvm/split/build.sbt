import scala.util.Properties
import ai.kien.scalapy.plugin.ScalaPyJVMPlugin.scalapySettings

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "me.shadaj" %% "scalapy-core" % "0.5.2",
      "org.scalatest" %% "scalatest" % "3.2.11" % Test
    ),
    inConfig(Test)(scalapySettings)
  )

lazy val pythonExecutable = Properties.propOrNone("plugin.python.executable")

inThisBuild(
  pythonExecutable
    .map(p => Def.settings(scalapyPythonExecutable := p))
    .getOrElse(Def.settings())
)
