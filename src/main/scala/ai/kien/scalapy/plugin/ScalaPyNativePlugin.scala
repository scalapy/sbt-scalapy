package ai.kien.scalapy.plugin

import sbt._

import scalanative.sbtplugin.ScalaNativePlugin
import scalanative.sbtplugin.ScalaNativePlugin.autoImport._
import ScalaPyPlugin.autoImport._

object ScalaPyNativePlugin extends AutoPlugin {
  override def trigger: PluginTrigger = allRequirements
  override def requires: Plugins = ScalaPyPlugin && ScalaNativePlugin

  object autoImport {
    val scalapyLinkingOptions = settingKey[Seq[String]](
      "Python linker options."
    )
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = Seq(
    scalapyLinkingOptions := (
      scalapyLinkingOptions or Def.setting(
        scalapyPython.value.ldflags.get
      )
    ).value,
    nativeLinkingOptions ++= scalapyLinkingOptions.value
  )

  lazy val scalapySettings: Seq[Setting[_]] =
    projectSettings ++ ScalaPyPlugin.projectSettings
}
