package test

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.PropertyChecks

import scala.annotation.tailrec
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

abstract class UnitPropertySpec extends {}
    with PropSpec
    with PropertyChecks
    with Matchers {

    /** Run expression at an interval until timeout is hit */
    protected def pollExpr(interval: FiniteDuration, timeout: FiniteDuration)(expr: => Unit) : Unit = {

      val startTime = System.nanoTime().nanos

      @tailrec
      def execExpr(currentTime: FiniteDuration): Unit = {
        try {
          expr
        } catch {
          case e: Throwable  =>
            if (currentTime - startTime > timeout) {
              throw new RuntimeException(s"${e.getMessage} (tried for ${timeout.toMillis} millis)", e)
            }
            Thread.sleep(interval.toMillis)
            execExpr(System.nanoTime().nanos)
        }
      }
      execExpr(startTime)
    }
  }
