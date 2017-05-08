package io.voklim.mon.monitor.buffered.watcher

/** Watcher is responsible for running implementations of [[com.weather.flux.monitor.manager.MonitorManager]] */
trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
