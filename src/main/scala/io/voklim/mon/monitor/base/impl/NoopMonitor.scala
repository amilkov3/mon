package io.voklim.mon.monitor.base.impl

import io.voklim.mon.monitor.buffered.BufferedMonitor
import io.voklim.common._

/** Stub implementation of Monitor which does nothing. */
object NoopMonitor extends BufferedMonitor {

  override def send(metric: Metric, metrics: Metric*): Unit = ()

  override def iterNextChunk(): Seq[Metric] = Vector.empty[Metric]

  override def push(metric: Metric, metrics: Metric*): Unit = ()
}
