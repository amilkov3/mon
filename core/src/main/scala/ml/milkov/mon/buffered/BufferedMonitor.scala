package ml.milkov.mon.buffered

import ml.milkov.mon.base.Monitor
import ml.milkov.internal.common._

/** Trait for monitor which accumulates metrics in memory before sending them */
trait BufferedMonitor[F[_]] extends Monitor[F] {
  def push(metric: Metric, metrics: Metric*): Unit
  def iterNextChunk(): Seq[Metric]
}
