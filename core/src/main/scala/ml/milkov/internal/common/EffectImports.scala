package ml.milkov.internal.common

trait EffectImports {

  type Effect[F[_]] = cats.effect.Effect[F]
  val Effect = cats.effect.Effect

  type Unsafe[F[_]] = effect.Unsafe[F]
  val Unsafe= effect.Unsafe

  type Id[A] = cats.Id[A]

  type Task[+A] = monix.eval.Task[A]
  val Task = monix.eval.Task

  type IO[+A] = cats.effect.IO[A]
  val IO = cats.effect.IO

  implicit def toUnsafeOps[F[_]: Unsafe, A](x: F[A]): effect.UnsafeOps[F, A] = new effect.UnsafeOps[F, A](x)

}
