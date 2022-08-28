import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.{PyQuote, SeqConverters}

import scala.util.{Try, Success, Failure}

object Main {
  def main(args: Array[String]): Unit = {
    (try {
      py.Dynamic.global.list(Seq(1, 2, 3).toPythonCopy)
      Success(true)
    } catch {
      case t: Throwable => Failure(t)
    }) fold (
      t => println(s"Successfully failed with $t}"),
      _ => sys.error("This should not succeed")
    )
  }
}
