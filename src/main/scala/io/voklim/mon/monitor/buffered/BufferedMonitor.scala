package io.voklim.mon.monitor.buffered

import io.voklim.mon.monitor.base.Monitor
import io.voklim.mon.common._

/** Trait for monitor which accumulates metrics in memory before sending them */
trait BufferedMonitor extends Monitor {
  def push(metric: Metric, metrics: Metric*): Unit
  def iterNextChunk(): Seq[Metric]
}
