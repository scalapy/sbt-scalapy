enablePlugins(ScalaNativePlugin, ScalaPyPlugin)
libraryDependencies += "me.shadaj" %%% "scalapy-core" % "0.5.1"

ThisBuild / nativeLinkStubs := true
