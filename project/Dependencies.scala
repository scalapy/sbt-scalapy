import sbt._

object Dependencies {
  lazy val pythonNativeLibs = "ai.kien" %% "python-native-libs" % "0.2.3"
  lazy val sbtScalaNative = "org.scala-native" % "sbt-scala-native" % "0.4.12"
  lazy val organizeImports = "com.github.liancheng" %% "organize-imports" % "0.6.0"
}
