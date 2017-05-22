package io.voklim.internal.common

trait ExtensionImports {
  implicit def richOption[A](repr: Option[A]): RichOption[A] = new RichOption[A](repr)
}

final class RichOption[A](val repr: Option[A]) extends AnyVal {
  def cata[B](f: => B, g: A => B): B = repr match {
    case None => f
    case Some(x) => g(x)
  }
}
