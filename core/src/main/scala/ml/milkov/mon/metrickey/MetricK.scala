package ml.milkov.mon.metrickey

/** Trait ALL metric key types must extend */
trait MetricK {
  def aggregate: Boolean
}
