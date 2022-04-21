# Automatic SBT configuration for ScalaPy

## Quick start

1. Add the plugin to `project/plugins.sbt`

    for JVM,

    ```scala
    addSbtPlugin("ai.kien" % "sbt-scalapy" % "<version>")
    ```

    for Scala Native,

    ```scala
    addSbtPlugin("ai.kien" % "sbt-scalapy-native" % "<version>")
    ```

2. Enable the plugin for your ScalaPy project in `build.sbt`

    ```scala
    enablePlugins(ScalaPyPlugin)
    ```

    `sbt-scalapy` would then configure the project to use the `python` in your current environment. If you want to use another Python version, set the `scalapyPythonExecutable` key to the interpreter executable absolute path,

    ```scala
    scalapyPythonExecutable := "/absolute/path/to/python"
    ```
