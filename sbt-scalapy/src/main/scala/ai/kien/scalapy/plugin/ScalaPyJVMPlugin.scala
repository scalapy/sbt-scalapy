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
    val scalapyPythonNativeLibrary = settingKey[String](
      "Name of the libpython to use, e.g. python3.8, python3.7m."
    )

    val scalapyPythonNativeLibraryPaths = settingKey[Seq[String]](
      "libpython will be looked for in these locations."
    )
  }

  import autoImport._

  private val scalapyPythonNativeLibraryOpt = SettingKey.local[Option[String]]
  private val scalapyPythonNativeLibraryPathsOpt = SettingKey.local[Option[Seq[String]]]

  override def globalSettings: Seq[Setting[_]] = Seq(
    scalapyPythonNativeLibraryOpt := None,
    scalapyPythonNativeLibraryPathsOpt := None
  )

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
    Def.derive(
      javaOptions ++=
        Map(
          "jna.library.path" -> appendToJNALibraryPath(
            scalapyPythonNativeLibraryPathsOpt.value.getOrElse(
              scalapyPython.value.nativeLibraryPaths.get
            )
          ),
          "scalapy.python.library" ->
            scalapyPythonNativeLibraryOpt.value.getOrElse(scalapyPython.value.nativeLibrary.get),
          "scalapy.python.programname" -> {
            for {
              _ <- scalapyPythonNativeLibraryPathsOpt.value
              _ <- scalapyPythonNativeLibraryOpt.value
              s <- scalapyPythonExecutableOpt.value
            } yield s
          }.getOrElse(scalapyPython.value.executable.get)
        ).map { case (k, v) => s"""-D$k=$v""" }.toSeq
    ),
    Def.derive(
      scalapyPythonNativeLibraryOpt := Some(scalapyPythonNativeLibrary.value)
    ),
    Def.derive(
      scalapyPythonNativeLibraryPathsOpt := Some(scalapyPythonNativeLibraryPaths.value)
    )
  )
}
