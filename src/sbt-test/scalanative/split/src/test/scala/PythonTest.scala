import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.{PyQuote, SeqConverters}
import org.scalatest.funsuite.AnyFunSuite

class PythonTest extends AnyFunSuite {
  test("ScalaPy runs successfully") {
    py.Dynamic.global.list(Seq(1, 2, 3).toPythonCopy)
    py"'Hello from ScalaPy!'"
    if (System.getenv.containsKey("CI_VIRTUALENV")) {
      println(s"Successfully load ${py.module("dummy")}")
    }
  }
}
