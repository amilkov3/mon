package ml.milkov.internal.common.data

import io.estatico.newtype._
import java.util.Date
import java.time.Instant

object Timestamp extends NewType.Default[Long] {
  def now(): Type = wrap(System.currentTimeMillis())
}

trait Timestamplike[A] {
  def fromTimestamp(a: Timestamp.Type): A
}

object Timestamplike {
  def apply[A](implicit ev: Timestamplike[A]): Timestamplike[A] = ev

  implicit val timestamplikeDate: Timestamplike[Date] = new Timestamplike[Date] {
    override def fromTimestamp(a: Timestamp.Type): Date = Date.from(Instant.ofEpochMilli(a.repr))
  }
}
