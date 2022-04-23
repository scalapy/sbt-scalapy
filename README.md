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

## Advanced

### JVM

ScalaPy for JVM requries the name and location of `libpython`. You can set these manually instead of letting the plugin automatically figure it out,

```scala
scalapyPythonNativeLibrary := "python3.9m"
scalapyPythonNativeLibraryPaths := Seq(
  "/first/directory/to/look/for/libpython",
  "/second/directory/to/look/for/libpython"
)
```

### Scala Native

ScalaPy for Scala Native requries [the linker flags for embedding Python](https://docs.python.org/3/extending/embedding.html#compiling-and-linking-under-unix-like-systems). You can set this manually instead of letting the plugin automatically figure it out,

```scala
scalapyLinkingOptions := Seq("-l...", "-l...")
```
