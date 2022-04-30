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

  private val scalapyLinkingOptionsOpt = SettingKey.local[Option[Seq[String]]]

  override def globalSettings: Seq[Setting[_]] = Seq(
    scalapyLinkingOptionsOpt := None
  )

  override def projectSettings: Seq[Setting[_]] = Seq(
    Def.derive(
      nativeLinkingOptions ++=
        scalapyLinkingOptionsOpt.value.getOrElse(scalapyPython.value.ldflags.get)
    ),
    Def.derive(scalapyLinkingOptionsOpt := Some(scalapyLinkingOptions.value))
  )
}
