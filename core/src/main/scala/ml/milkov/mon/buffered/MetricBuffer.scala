package ml.milkov.mon.buffered

import ml.milkov.internal.common._
import ml.milkov.mon.metrickey.MetricK

/** Interface for an in memory metric buffer that supports
  * concurrent push */
private[milkov] abstract class MetricBuffer[A <: MetricK: Show] {

  /** Push to buffer concurrently */
  def push(metrics: Metric[A]*): Unit

  /** Upstream impl will call this to pull chunk from buffer */
  def iterNextChunk(): List[Metric[A]]
}
