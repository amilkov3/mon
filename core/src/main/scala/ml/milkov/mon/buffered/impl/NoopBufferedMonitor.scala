package ml.milkov.mon.buffered.impl

import ml.milkov.mon.metrics.MetricKey
import ml.milkov.mon.buffered.BufferedMonitor
import ml.milkov.internal.common._

object NoopBufferedMonitor extends BufferedMonitor[Id] {

  override def send(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): Unit = ()

  override def iterNextChunk(): Seq[Metric] = List.empty[Metric]

  override def push(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): Unit = ()
}
