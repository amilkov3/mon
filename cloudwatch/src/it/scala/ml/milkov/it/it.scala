package ml.milkov

import ml.milkov.config.CloudwatchConf
import ml.milkov.mon.metrics._

import scala.concurrent.duration.{FiniteDuration, _}

package object it {
  trait TestConf extends {}
    with config.BufferedMonitorConf
    with config.MonitorWatcherConf
    with CloudwatchConf

  val testConf = new TestConf {
    override val flushMetricsCount: Int = 50
    override val namespace: String = "mon"
    override val sendMetricsInterval: FiniteDuration = 5.seconds
    override val sendBufferSize: Boolean = true
    override val bufferSizeMetricName: Option[MetricKey] = None
  }

  val metricTestKey = MetricKey.createKey(
    new MetricPrefix("testPrefix"),
    new MetricDomain("testDomain"),
    new MetricName("testName")
  )
}
