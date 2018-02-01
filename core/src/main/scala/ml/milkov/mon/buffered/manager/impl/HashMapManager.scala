package ml.milkov.mon.buffered.manager.impl

import ml.milkov.mon.buffered.BufferedMonitor
import ml.milkov.mon.buffered.manager.MonitorManager
import ml.milkov.mon.metrics.MetricKey
import ml.milkov.internal.common._

/** Monitor manager that stores metric state in an intermediate hashmap
  * (since certain metrics can be configured to be zeroed out or not every time
  *exec they are sent) */
class HashMapManager[F[_]: Effect](bufferedMonitor: BufferedMonitor[F]) extends MonitorManager[F] {

  private val metricMap = new scala.collection.mutable.HashMap[MetricKey, Double]()

  override def sendChunk(): F[Unit] = {
    updateMap()
    val clone = metricMap.map { case (k, v) =>
      if (k.metricName.clearOnSend) metricMap.update(k, 0)
      k -> v
    }
    val cloneSeq = clone.toSeq
    bufferedMonitor.send(cloneSeq.head, cloneSeq.tail: _*)
  }

  private def updateMap() = {
    bufferedMonitor.iterNextChunk().foreach{ case (k, v) =>
      metricMap.get(k).cata({metricMap += (k -> v)}, oldVal => metricMap.update(k, oldVal + v))
    }
  }

  /** For testing */
  private[buffered] def getMapSnapshot(): Map[MetricKey, Double] = metricMap.toMap
}
