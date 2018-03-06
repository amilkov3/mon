package ml.milkov.mon.config

import ml.milkov.internal.common._

import scala.concurrent.duration.FiniteDuration

abstract class MetricBufferConf[A <: MetricK: Show] {
  def flushMetricsCount: Int

  /** If non-empty, every time metrics are flushed, the count of
    * the remaining metrics in the buffered monitor will be
    * sent as a metric. This will allow you to adjust
    * [[MonitorWatcherConf.sendMetricsInterval]]
    * or [[flushMetricsCount]] accordingly
    *  */
  def bufferSizeMetricName: Option[A]
}

trait MonitorWatcherConf {
  def sendMetricsInterval: FiniteDuration
}
