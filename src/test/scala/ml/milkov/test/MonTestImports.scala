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

  private val pre = MetricPrefix.tag("testPrefix")
  private val dom = MetricDomain.tag("testDomain")

  val metricTestKey = MetricKey.createKey(
    pre,
    dom,
    MetricName.tag("testName")
  )

  val aggTestKey = MetricKey.createKey(
    pre,
    dom,
    MetricName.tag("aggTestName", true)
  )

  def populatedQueue(buff: QueueMetricBuffer[MetricKey]) = {
    0.until(testConf.flushMetricsCount / 2).foreach { _ =>
      buff.push((metricTestKey, Timestamp.now(), 1))
      buff.push((aggTestKey, Timestamp.now(), 1))
    }
  }
}
