package ml.milkov.mon.buffered.manager.impl

import ml.milkov.mon.buffered.MetricBuffer
import ml.milkov.mon.buffered.manager.MonitorManager
import ml.milkov.internal.common._
import ml.milkov.mon.base.Monitor
import ml.milkov.mon.metrickey

import scala.collection.mutable.{HashMap, ListBuffer}

/** Monitor manager that stores state for aggregate metrics in a map */
private[milkov] final class HashMapManager[F[_]: Effect, A <: metrickey.MetricK: Show](
  buffer: MetricBuffer[A],
  val mon: Monitor[F, A]
) extends MonitorManager[F] {

  private val metricMap = new HashMap[A, ListBuffer[(Timestamp, Double)]]()

  /** Update map state and then take a snapshot of said state and send it
    * upstream to metrics service via client */
  override def sendChunk(): F[Unit] = {
    updateMap()
    mon.send(
      metricMap.foldLeft(List.empty[(A, Timestamp, Double)]) {case (outs, (k, v)) =>
        val kv = k.aggregate.fold(
          {
            val vNew = v.take(v.length-1)
            v.remove(0, v.length - 1)
            vNew
          },
          {
            val vNew = v.take(v.length)
            v.remove(0, v.length)
            vNew
          }
        )
        outs.:::(kv.map{case (a, b) => (k, a, b)}.toList)
      }: _*
    )
  }

  /** Pull chunk from queue and update map state */
  private def updateMap() = {
    // TODO:
    /** Inference bug in scala means have to be explicit that each
    block returns unit () */
    buffer.iterNextChunk().foreach{ case (k, t, v) =>
      metricMap.get(k).cata(
        oldVal => {k.aggregate.fold(
          oldVal += ((t, v + oldVal.last._2)),
          oldVal += ((t, v))
        )
          ()
        },
        {metricMap += (k -> ListBuffer((t, v)))
          ()
        }
      )
      ()
    }
  }

  /** For testing */
  private[buffered] def getMapSnapshot(): Map[A, ListBuffer[(Timestamp, Double)]] =
    metricMap.toMap
}
