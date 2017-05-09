package io.voklim.mon.monitor.buffered.watcher

/** Watcher is responsible for running implementations of [[io.voklim.mon.monitor.buffered.manager.MonitorManager]] */
trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
