package ml.milkov.test

import ml.milkov.mon.config.{MetricBufferConf, MonitorWatcherConf}
import ml.milkov.mon.buffered.QueueMetricBuffer
import ml.milkov.internal.common._
import ml.milkov.mon.metrickey.{MetricDomain, MetricKey, MetricName, MetricPrefix}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

trait MonTestImports {

  trait TestConf extends {}
    with MetricBufferConf[MetricKey]
    with MonitorWatcherConf

  val testConf = new TestConf {
    override val flushMetricsCount: Int = 50
    override val sendMetricsInterval: FiniteDuration = 5.seconds
    override val bufferSizeMetricName: Option[MetricKey] = None
  }

  val metricTestKey = MetricKey.createKey(
    MetricPrefix.tag("testPrefix"),
    MetricDomain.tag("testDomain"),
    MetricName.tag("testName")
  )

  def populatedQueue(buff: QueueMetricBuffer[MetricKey]) = {
    1.to(testConf.flushMetricsCount).foreach { _ =>
      buff.push((metricTestKey, Timestamp.now(), 1))
    }
  }
}
