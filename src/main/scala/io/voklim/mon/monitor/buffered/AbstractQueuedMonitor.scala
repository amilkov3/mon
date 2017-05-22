package io.voklim.mon.monitor.buffered

import scala.collection.JavaConverters._
import io.voklim.config.BufferedMonitorConf
import io.voklim.internal.common._
import io.voklim.mon.metrics._

/** Abstract buffered monitor that accumulates metrics in a
  * concurrent queue */
abstract class AbstractQueuedMonitor(conf: BufferedMonitorConf) extends {}
  with BufferedMonitor
  with Logging
{

  private val metricQueue = new java.util.concurrent.ConcurrentLinkedQueue[Metric]()

  override def push(metric: Metric, metrics: Metric*): Unit = {
    val metricSeq = (metric +: metrics)
    logger.debug(s"Adding ${metricSeq.length} metric(s) to metric queue")
    metricQueue.addAll(metricSeq.asJavaCollection)
  }

  override def iterNextChunk(): List[Metric] = {
    val ms = Iterator.continually(metricQueue.poll()).takeWhile(_ != null).take(conf.flushMetricsCount).toList
    val remainingSize = metricQueue.size()
    logger.info(s"Flushed ${ms.length} metrics from metric queue. $remainingSize remain in queue")
    if (conf.sendBufferSize) {
      val metricName = conf.bufferSizeMetricName.cata(
        bufferSizeMetricName,
        identity(_)
      )
      (metricName, remainingSize.toDouble) :: ms
    } else {
      ms
    }
  }

  /** Default key if one is not provided */
  private val bufferSizeMetricName = MetricKey.createKey(
    new MetricPrefix("info"),
    new MetricDomain("bufferedMonitor"),
    new MetricName("size")
  )
}
