package io.voklim.test

import io.voklim.config.{BufferedMonitorConf, CloudwatchConf, MonitorWatcherConf}
import io.voklim.mon.metrics.{MetricDomain, MetricKey, MetricName, MetricPrefix}
import io.voklim.mon.monitor.buffered.AbstractQueuedMonitor

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

trait MonTestImports {

  trait TestConf extends {}
    with BufferedMonitorConf
    with MonitorWatcherConf
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

  def populatedQueue(queuedMonitor: AbstractQueuedMonitor) = {
    1.to(testConf.flushMetricsCount).foreach { _ =>
      queuedMonitor.push((metricTestKey, 1))
    }
  }
}
