package io.voklim.mon.monitor.buffered.manager.impl

import io.voklim.mon.monitor.buffered.BufferedMonitor
import io.voklim.mon.monitor.buffered.manager.MonitorManager
import io.voklim.mon.metrics.MetricKey
import io.voklim.common._

/** Monitor manager that stores metric state in an intermediate hashmap
  * (since certain metrics can be configured to be zeroed out or not every time
  * they are sent) */
class HashMapManager(bufferedMonitor: BufferedMonitor) extends MonitorManager {

  private val metricMap = new scala.collection.mutable.HashMap[MetricKey, Double]()

  override def sendChunk(): Unit = {
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
