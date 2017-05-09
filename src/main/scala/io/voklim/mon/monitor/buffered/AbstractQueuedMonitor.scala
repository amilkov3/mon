package io.voklim.mon.monitor.buffered

import scala.collection.JavaConverters._
import io.voklim.config.BufferedMonitorConf
import io.voklim.common._

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

  override def iterNextChunk(): Vector[Metric] = {
    val ms = Iterator.continually(metricQueue.poll()).takeWhile(_ != null).take(conf.flushMetricsCount).toVector
    logger.info(s"Flushed ${ms.length} metrics from metric queue. ${metricQueue.size()} remain in queue")
    ms
  }
}
