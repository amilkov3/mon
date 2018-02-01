package ml.milkov.test

import ml.milkov.config.{BufferedMonitorConf, MonitorWatcherConf}
import ml.milkov.mon.metrics.{MetricDomain, MetricKey, MetricName, MetricPrefix}
import ml.milkov.mon.buffered.AbstractQueuedMonitor
import ml.milkov.internal.common._

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

trait MonTestImports {

  trait TestConf extends {}
    with BufferedMonitorConf
    with MonitorWatcherConf

  val testConf = new TestConf {
    override val flushMetricsCount: Int = 50
    override val sendMetricsInterval: FiniteDuration = 5.seconds
    override val sendBufferSize: Boolean = true
    override val bufferSizeMetricName: Option[MetricKey] = None
  }

  val metricTestKey = MetricKey.createKey(
    new MetricPrefix("testPrefix"),
    new MetricDomain("testDomain"),
    new MetricName("testName")
  )

  def populatedQueue(queuedMonitor: AbstractQueuedMonitor[Id]) = {
    1.to(testConf.flushMetricsCount).foreach { _ =>
      queuedMonitor.push((metricTestKey, 1))
    }
  }
}
