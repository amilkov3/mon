package mon.buffered.manager

import mon.base.Monitor

/** Trait for manager which is responsible for invoking [[Monitor.send]]
  * i.e. impl takes a [[Monitor] as a dependency */
trait MonitorManager[F[_]] {

  /** Defers to dependency's impl of [[Monitor.send]]  */
  def sendChunk(): F[Unit]
}
