package ml.milkov.mon.buffered.watcher

import ml.milkov.mon.buffered.manager.MonitorManager

/** Watcher is responsible for running implementations of [[MonitorManager]] */
private[milkov] trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
