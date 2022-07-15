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

  override def projectSettings: Seq[Setting[_]] = Seq(
    scalapyPython := Python((scalapyPythonExecutable ?).value)
  )
}
