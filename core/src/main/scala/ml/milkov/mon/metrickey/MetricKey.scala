package ml.milkov.mon.metrickey

import ml.milkov.internal.common._
import ml.milkov.mon.base.Monitor

/** Default implementation of [[MetricK]] if user wants to just use
  * this out of the box */
sealed abstract case class MetricKey(
   keyPrefix: MetricPrefix.Type,
   keyDomain: MetricDomain.Type,
   metricName: MetricName.Type,
   suffixParts: Option[String] = None
) extends MetricK {
  final def toKeyString: String = {

    val baseVector =Vector(
      keyPrefix.toString,
      keyDomain.toString,
      metricName.toString
    ).mkString(".")

    suffixParts.cata(suffix => s"$baseVector.$suffix", baseVector)
  }

  override val aggregate: Boolean = metricName.aggregate
}
/** Produces the appropriate metric names for a [[Monitor]]. */
object MetricKey {

  /** Key may have suffix parts such as geocode and units */
  def createWithSuffix(
    keyPrefix: MetricPrefix.Type,
    keyDomain: MetricDomain.Type,
    metricName: MetricName.Type,
    keySuffix: String*
  ): MetricKey = {
    new MetricKey(keyPrefix, keyDomain, metricName, Some(keySuffix.mkString("."))) {}
  }

  def createKey(keyPrefix: MetricPrefix.Type, keyDomain: MetricDomain.Type, metricName: MetricName.Type): MetricKey = {
    new MetricKey(keyPrefix, keyDomain, metricName) {}
  }

  implicit val showMetricKey: Show[MetricKey] = new Show[MetricKey] {
    override def show(t: MetricKey): String = t.toKeyString
  }
}

/** Tagged type representing a prefix i.e. "my.service" */
object MetricPrefix extends NewType.Default[String]{
  def tag(s: String) = wrap(s)
}

/** Tagged type representing a domain i.e. "akka" */
object MetricDomain extends NewType.Default[String]{
  def tag(s: String) = wrap(s)
}

/** Tagged type representing a domain i.e. "activeActors"
  * `aggregate` denotes whether metric should be cumulative */
object MetricName extends NewType.Default[(String, Boolean)]{
  def tag(s: String, aggregate: Boolean = true) = wrap((s, aggregate))

  implicit final class Ops(val repr: Type) extends AnyVal {
    def aggregate: Boolean = unwrap(repr)._2
  }
}
