package mon.buffered

import mon.internal.common._
import mon.metrickey.MetricK

/** Interface for an in memory metric buffer that supports
  * concurrent push */
abstract class MetricBuffer[A <: MetricK: Show] {

  /** Push to buffer concurrently */
  def push(metrics: Metric[A]*): Unit

  /** Upstream impl will call this to pull chunk from buffer */
  def iterNextChunk(): List[Metric[A]]
}
