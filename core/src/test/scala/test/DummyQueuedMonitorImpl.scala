package test

import mon.config.MetricBufferConf
import mon.internal.common._
import mon.base.Monitor
import mon.buffered.MetricBuffer
import mon.metrickey.MetricK

class DummyMonitor extends Monitor[Id, MetricKey] with Logging {
  override def send(metrics: Metric[MetricKey]*): Id[Unit] = {
    metrics.foreach{case (k, t, v) =>
      logger.debug(
        s"Sending metric with key -> ${k.show}, timestamp -> ${t.repr} and value -> $v "
      )
    }
  }
}
