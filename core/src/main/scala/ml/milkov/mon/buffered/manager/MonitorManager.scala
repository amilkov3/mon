package ml.milkov.mon.buffered.manager

import ml.milkov.internal.common._

/** Trait for management of a monitor */
trait MonitorManager[F[_]] {
  def sendChunk(): F[Unit]
}
