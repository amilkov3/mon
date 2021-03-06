package mon.internal.common

import mon.internal.common._

trait IdInstances {

  implicit val unsafe: Unsafe[Id] = new Unsafe[Id] {
    override def unsafePerformSync[A](fa: Id[A]): A = fa
  }

  object Id

  implicit val effectId: Effect[Id] =  new Effect[Id] {
    override def runAsync[A](fa: Id[A])(cb: (Either[Throwable, A]) => IO[Unit]): IO[Unit] = IO(())
    override def async[A](k: ((Either[Throwable, A]) => Unit) => Unit): Id[A] = Id.asInstanceOf[A]
    override def suspend[A](thunk: => Id[A]): Id[A] = thunk
    override def raiseError[A](e: Throwable): Id[A] = Id.asInstanceOf[A]
    override def handleErrorWith[A](fa: Id[A])(f: (Throwable) => Id[A]): Id[A] = fa
    override def pure[A](x: A): Id[A] = x
    override def tailRecM[A, B](a: A)(f: (A) => Id[Either[A, B]]): Id[B] = Id.asInstanceOf[B]
    override def flatMap[A, B](fa: Id[A])(f: (A) => Id[B]): Id[B] = Id.asInstanceOf[B]
  }

}
