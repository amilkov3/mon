package ml.milkov.mon.buffered

import ml.milkov.internal.common._
import scala.collection.JavaConverters._
import ml.milkov.mon.config.MetricBufferConf

/** Buffer that accumulates metrics in a
  * concurrent queue */
private[milkov] final class QueueMetricBuffer[A <: MetricK: Show](
  conf: MetricBufferConf[A]
) extends {}
  with MetricBuffer[A]
  with Logging {

  private val metricQueue = new java.util.concurrent.ConcurrentLinkedQueue[Metric[A]]()

  override def push(metrics: Metric[A]*): Unit = {
    logger.debug(s"Adding ${metrics.length} metric(s) to metric queue")
    metricQueue.addAll(metrics.asJavaCollection)
  }

  override def iterNextChunk(): List[Metric[A]] = {
    val ms = Iterator.continually(metricQueue.poll()).takeWhile(_ != null).take(conf.flushMetricsCount).toList
    val remainingSize = metricQueue.size()
    logger.info(s"Flushed ${ms.length} metrics from metric queue. $remainingSize remain in queue")
    conf.bufferSizeMetricName.cata(
      (_, Timestamp.now(), remainingSize.toDouble) :: ms,
      ms
    )
  }
}
