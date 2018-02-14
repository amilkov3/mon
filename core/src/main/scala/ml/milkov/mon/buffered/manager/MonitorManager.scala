package ml.milkov.mon.buffered.manager

import ml.milkov.mon.base.Monitor

/** Trait for manager which is responsible for invoking [[Monitor.send]]
  * i.e. impl takes a [[Monitor] as a dependency */
private[milkov] trait MonitorManager[F[_]] {

  /** Defers to dependency's impl of [[Monitor.send]]  */
  def sendChunk(): F[Unit]
}
