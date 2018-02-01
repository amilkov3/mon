package ml.milkov.config

import ml.milkov.mon.metrics.MetricKey

import scala.concurrent.duration.FiniteDuration

trait BufferedMonitorConf {
  def flushMetricsCount: Int
  /** If true, every time metrics are flushed, the count of
    * the remaining metrics in the buffered monitor will be
    * sent as a metric. This will allow you to adjust
    * [[MonitorWatcherConf.sendMetricsInterval]]
    * or [[flushMetricsCount]] accordingly
    *  */
  def sendBufferSize: Boolean
  def bufferSizeMetricName: Option[MetricKey]
}

trait MonitorWatcherConf {
  def sendMetricsInterval: FiniteDuration
}
