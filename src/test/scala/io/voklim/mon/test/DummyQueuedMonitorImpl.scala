package io.voklim.mon.test

import io.voklim.mon.config.BufferedMonitorConf
import io.voklim.mon.monitor.buffered.AbstractQueuedMonitor
import io.voklim.mon.common._

class DummyQueuedMonitorImpl(conf: BufferedMonitorConf) extends AbstractQueuedMonitor(conf) {
  override def send(metric: Metric, metrics: Metric*): Unit = {
    (metric +: metrics).foreach { case (k, v) =>
      println(s"Sending metric with key -> ${k.toKeyString} and value: $v")
    }
  }
}
