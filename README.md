# Automatic SBT configuration for ScalaPy

*supports both JVM and/or Scala Native projects*

![Build Status](https://github.com/kiendang/sbt-scalapy/actions/workflows/ci.yml/badge.svg)
## Quick start

1. Add the plugin to `project/plugins.sbt`

    ```scala
    addSbtPlugin("ai.kien" % "sbt-scalapy" % "<version>")
    ```

2. Enable the plugin for your ScalaPy project in `build.sbt`

    ```scala
    enablePlugins(ScalaPyPlugin)
    ```

    `sbt-scalapy` would then configure the project to use the `python` in your current environment. If you want to use another Python version, set the `scalapyPythonExecutable` key to the interpreter executable,

    could be the absolute path

    ```scala
    scalapyPythonExecutable := "/absolute/path/to/python"
    ```

    or just the name of the executable if it's already in `PATH`

    ```scala
    scalapyPythonExecutable := "python3.9"
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

## Virtualenv

To use a virtualenv Python, you can either activate the virtualenv then start sbt or set `scalapyPythonExecutable` to the virtualenv Python executable.

For Scala Native, you also need to set the `SCALAPY_PYTHON_PROGRAMNAME` environment variable to the virtualenv Python executable,

```sh
SCALAPY_PYTHON_PROGRAMNAME="/path/to/python" sbt
```
