package ai.kien.scalapy.plugin

import ai.kien.python.Python
import sbt._

object ScalaPyPlugin extends AutoPlugin {
  object autoImport {
    val scalapyPythonExecutable = settingKey[String](
      "Absolute path to the Python interpreter executable."
    )

    val scalapyPython = settingKey[Python](
      "Python configuration. Not intended to be accessed directly by users."
    )

    private[plugin] val scalapyPythonExecutableOpt = SettingKey.local[Option[String]]
  }

  import autoImport._

  override def globalSettings: Seq[Setting[_]] = Seq(
    scalapyPythonExecutableOpt := None
  )

  override def projectSettings: Seq[Setting[_]] = Seq(
    Def.derive(scalapyPython := Python(scalapyPythonExecutableOpt.value)),
    Def.derive(
      scalapyPythonExecutableOpt := Some(scalapyPythonExecutable.value)
    )
  )
}
