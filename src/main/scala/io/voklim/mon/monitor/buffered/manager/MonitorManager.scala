package io.voklim.mon.monitor.buffered.manager

/** Trait for management of a monitor */
trait MonitorManager {
  def sendChunk(): Unit
}
