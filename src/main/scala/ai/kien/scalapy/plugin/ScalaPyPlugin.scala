package ai.kien.scalapy.plugin

import ai.kien.python.Python
import sbt._

object ScalaPyPlugin extends AutoPlugin {
  object autoImport {
    val scalapyPythonExecutable = settingKey[String](
      "Absolute path to the Python interpreter executable."
    )

    val scalapyPython = settingKey[Python]("")
  }

  import autoImport._

  private val scalapyPythonExecutableOpt = SettingKey.local[Option[String]]

  override def globalSettings: Seq[Setting[_]] = Seq(
    scalapyPythonExecutableOpt := None
  )

  override def projectSettings: Seq[Setting[_]] = Seq(
    Def.derive(scalapyPythonExecutableOpt := Some(scalapyPythonExecutable.value)),
    scalapyPython := Python(scalapyPythonExecutableOpt.value)
  )
}
