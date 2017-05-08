package io.voklim.mon.metrics

import io.voklim.mon.monitor.base.Monitor
import io.voklim.mon.common._

sealed abstract case class MetricKey(
   keyPrefix: MetricPrefix,
   keyDomain: MetricDomain,
   metricName: MetricName,
   suffixParts: Option[String] = None
) {
  final def toKeyString: String = {

    val baseVector =Vector(
      keyPrefix.toString,
      keyDomain.toString,
      metricName.toString
    ).mkString(".")

    suffixParts.cata(baseVector, suffix => s"$baseVector.$suffix")
  }
}
/** Produces the appropriate metric names for a [[Monitor]]. */
object MetricKey {

  /** Key may have suffix parts such as geocode and units */
  def createWithSuffix(
    keyPrefix: MetricPrefix,
    keyDomain: MetricDomain,
    metricName: MetricName,
    keySuffix: String*
  ): MetricKey = {
    new MetricKey(keyPrefix, keyDomain, metricName, Some(keySuffix.mkString("."))) {}
  }

  def createKey(keyPrefix: MetricPrefix, keyDomain: MetricDomain, metricName: MetricName): MetricKey = {
    new MetricKey(keyPrefix, keyDomain, metricName) {}
  }
}

final class MetricPrefix(override val toString: String) extends AnyVal

/** Designates the domain to which a metric name may belong */
final class MetricDomain(override val toString: String) extends AnyVal

final case class MetricName(override val toString: String, clearOnSend: Boolean = true)
