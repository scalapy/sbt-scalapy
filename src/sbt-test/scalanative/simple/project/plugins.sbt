addSbtPlugin("org.scala-native" % "sbt-scala-native" % "0.4.0")

sys.props.get("plugin.version") match {
  case Some(v) => addSbtPlugin("ai.kien" % "sbt-scalapy" % v)
  case _ =>
    sys.error(
      """|The system property 'plugin.version' is not defined.
         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin
    )
}

resolvers +=
  "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots"
