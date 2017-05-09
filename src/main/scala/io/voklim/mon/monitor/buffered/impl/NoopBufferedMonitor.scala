package io.voklim.mon.monitor.buffered.impl

import io.voklim.mon.metrics.MetricKey
import io.voklim.mon.monitor.buffered.BufferedMonitor
import io.voklim.common._

object NoopBufferedMonitor extends BufferedMonitor{

  override def send(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): Unit = ()

  override def iterNextChunk(): Seq[Metric] = List.empty[Metric]

  override def push(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): Unit = ()
}
