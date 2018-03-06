package mon.buffered.watcher

import mon.buffered.manager.MonitorManager

/** Watcher is responsible for running implementations of [[MonitorManager]] */
trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
