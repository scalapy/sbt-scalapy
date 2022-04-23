enablePlugins(ScalaPyPlugin)
libraryDependencies += "me.shadaj" %% "scalapy-core" % "0.5.1"

lazy val python = ai.kien.python.Python()

scalapyPythonNativeLibrary := python.nativeLibrary.get
scalapyPythonNativeLibraryPaths := python.nativeLibraryPaths.get
