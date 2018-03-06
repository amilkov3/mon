package mon.internal

package object common extends {}
  with cats.effect.Effect.ToEffectOps
  with cats.syntax.ShowSyntax
  with io.estatico.newtype.ops.ToNewTypeOps
  with EffectImports
  with mouse.AllSyntax
  with DataImports
  with LoggingImports
  with IdInstances {

  type Show[A] = cats.Show[A]
  val Show = cats.Show

  type NewType = io.estatico.newtype.NewType
  val NewType = io.estatico.newtype.NewType
}
