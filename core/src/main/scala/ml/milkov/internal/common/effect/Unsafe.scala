package ml.milkov.internal.common.effect

import cats.syntax.either._
import ml.milkov.internal.common.{IO, Task}
import monix.execution.Scheduler

trait Unsafe[F[_]] {
  /** Run an effect synchronously */
  def unsafePerformSync[A](fa: F[A]): A

  /** Run an effect synchronously, catching a Throwable */
  def unsafeAttempt[A](fa: F[A]): Either[Throwable, A] = Either.catchNonFatal(unsafePerformSync(fa))
}

object Unsafe {

  def apply[F[_]](implicit ev: Unsafe[F]): Unsafe[F] = ev

  implicit val unsafeIO: Unsafe[IO] = new Unsafe[IO] {
    override def unsafePerformSync[A](fa: IO[A]): A = fa.unsafeRunSync()
  }

  implicit def unsafeTask(implicit sched: Scheduler): Unsafe[Task] = new Unsafe[Task] {
    override def unsafePerformSync[A](fa: Task[A]) : A = fa.coeval.value.right.get
  }


}

final class UnsafeOps[F[_], A](val repr: F[A]) extends AnyVal {

  def unsafePerformSync()(implicit ev: Unsafe[F]): A = ev.unsafePerformSync(repr)

  def unsafeAttempt()(implicit u: Unsafe[F]): Either[Throwable, A] = {
    Either.catchNonFatal(u.unsafePerformSync(repr))
  }
}
