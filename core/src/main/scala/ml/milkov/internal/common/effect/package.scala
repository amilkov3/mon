package ml.milkov.internal.common

import ml.milkov.mon.effect.UnsafeOps

package object effect

trait EffectImports {

  type Effect[F[_]] = cats.effect.Effect[F]
  val Effect = cats.effect.Effect

  type Unsafe[F[_]] = ml.milkov.mon.effect.Unsafe[F]
  val Unsafe= ml.milkov.mon.effect.Unsafe

  type Id[A] = cats.Id[A]

  type IO[+A] = cats.effect.IO[A]
  val IO = cats.effect.IO

  implicit def toUnsafeOps[F[_]: Unsafe, A](x: F[A]): UnsafeOps[F, A] = new UnsafeOps[F, A](x)

}
