package ml.milkov.test

import ml.milkov.mon.config.MetricBufferConf
import ml.milkov.internal.common._
import ml.milkov.mon.metrickey.MetricK

/*class DummyQueuedMonitorImpl(conf: MetricBufferConf[MetricKey]) extends AbstractQueuedMonitor[Id, MetricKey](conf) {
  override def send(metrics: Metric[MetricKey]*): Unit = {
    metrics.foreach { case (k, t, v) =>
      println(s"Sending metric with key -> ${k.show} and value: $v")
    }
  }
}*/
