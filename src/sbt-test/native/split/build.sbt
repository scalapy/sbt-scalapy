import scala.util.Properties
import ai.kien.scalapy.plugin.ScalaPyNativePlugin.scalapySettings

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    libraryDependencies ++= Seq(
      "me.shadaj" %%% "scalapy-core" % "0.5.2" % Test,
      "org.scalatest" %%% "scalatest" % "3.2.10" % Test
    ),
    inConfig(Test)(scalapySettings),
    TaskKey[Unit]("check") := {
      println(
        s"Compile / nativeLinkingOptions == ${(Compile / nativeLinkingOptions).??(Seq[String]()).value}"
      )
      println(
        s"Test / nativeLinkingOptions == ${(Test / nativeLinkingOptions).value}"
      )
      if (
        (Compile / nativeLinkingOptions)
          .??(Seq[String]())
          .value == (Test / nativeLinkingOptions).value
      ) {
        sys.error("Settings from ScalaPyNativePlugin should not have been applied to Compile.")
      }
    }
  )

lazy val pythonExecutable = Properties.propOrNone("plugin.python.executable")

inThisBuild(
  pythonExecutable
    .map(p => Def.settings(scalapyPythonExecutable := p))
    .getOrElse(Def.settings())
)
