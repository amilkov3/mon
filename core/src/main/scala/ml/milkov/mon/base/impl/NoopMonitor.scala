package ml.milkov.mon.base.impl

import ml.milkov.mon.buffered.BufferedMonitor
import ml.milkov.internal.common._

/** Stub implementation of Monitor which does nothing. */
object NoopMonitor extends BufferedMonitor[Id] {

  override def send(metric: Metric, metrics: Metric*): Unit = ()

  override def iterNextChunk(): Seq[Metric] = Vector.empty[Metric]

  override def push(metric: Metric, metrics: Metric*): Unit = ()
}
