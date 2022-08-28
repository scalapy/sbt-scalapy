package ai.kien.scalapy.plugin

import sbt.Keys._
import sbt._
import sbt.plugins.JvmPlugin

import java.io.File.pathSeparator
import scala.util.Properties

import ScalaPyPlugin.autoImport._

object ScalaPyJVMPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = ScalaPyPlugin && JvmPlugin

  object autoImport {
    val scalapyPythonProgramName = settingKey[String](
      "Absolute path to the Python interpreter executable as returned by py\"sys.executable\"."
    )

    val scalapyPythonNativeLibrary = settingKey[String](
      "Name of the libpython to use, e.g. python3.8, python3.7m."
    )

    val scalapyPythonNativeLibraryPaths = settingKey[Seq[String]](
      "Directory locations to look for libpython."
    )
  }

  import autoImport._

  private def appendToJNALibraryPath(nativeLibPaths: Seq[String]): String = {
    val currentPathsStr = Properties.propOrEmpty("jna.library.path")
    val currentPaths = currentPathsStr.split(pathSeparator)

    val pathsToAdd = if (currentPaths.containsSlice(nativeLibPaths)) Nil else nativeLibPaths
    val pathsToAddStr = pathsToAdd.mkString(pathSeparator)

    (currentPathsStr, pathsToAddStr) match {
      case (c, p) if c.isEmpty => p
      case (c, p) if p.isEmpty => c
      case (c, p)              => s"$p$pathSeparator$c"
    }
  }

  override def projectSettings: Seq[Setting[_]] = Seq(
    fork := true,
    scalapyPythonProgramName := (
      scalapyPythonProgramName or Def.setting(
        scalapyPython.value.executable.get
      )
    ).value,
    scalapyPythonNativeLibrary := (
      scalapyPythonNativeLibrary or Def.setting(
        scalapyPython.value.nativeLibrary.get
      )
    ).value,
    scalapyPythonNativeLibraryPaths := (
      scalapyPythonNativeLibraryPaths or Def.setting(
        scalapyPython.value.nativeLibraryPaths.get
      )
    ).value,
    javaOptions ++=
      Map(
        "jna.library.path" -> appendToJNALibraryPath(scalapyPythonNativeLibraryPaths.value),
        "scalapy.python.library" -> scalapyPythonNativeLibrary.value,
        "scalapy.python.programname" -> scalapyPythonProgramName.value
      ).map { case (k, v) => s"""-D$k=$v""" }.toSeq
  )

  lazy val scalapySettings: Seq[Setting[_]] =
    projectSettings ++ ScalaPyPlugin.projectSettings
}
