package mon.buffered.manager

/** Trait for manager which is responsible for invoking [[mon.base.Monitor.send]]
  * i.e. impl takes a [[mon.base.Monitor]] as a dependency */
trait MonitorManager[F[_]] {

  /** Defers to dependency's impl of [[mon.base.Monitor.send]]  */
  def sendChunk(): F[Unit]
}
