package ml.milkov.test

import ml.milkov.mon.config.MetricBufferConf
import ml.milkov.internal.common._
import ml.milkov.mon.base.Monitor
import ml.milkov.mon.buffered.MetricBuffer
import ml.milkov.mon.metrickey.MetricK

class DummyMonitor extends Monitor[Id, MetricKey] with Logging {
  override def send(metrics: Metric[MetricKey]*): Id[Unit] = {
    metrics.foreach{case (k, t, v) =>
      logger.debug(
        s"Sending metric with key -> ${k.show}, timestamp -> ${t.repr} and value -> $v "
      )
    }
  }
}
