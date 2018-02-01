package ml.milkov.test

import ml.milkov.config.BufferedMonitorConf
import ml.milkov.mon.buffered.AbstractQueuedMonitor
import ml.milkov.internal.common._

class DummyQueuedMonitorImpl(conf: BufferedMonitorConf) extends AbstractQueuedMonitor[Id](conf) {
  override def send(metric: Metric, metrics: Metric*): Unit = {
    (metric +: metrics).foreach { case (k, v) =>
      println(s"Sending metric with key -> ${k.toKeyString} and value: $v")
    }
  }
}
