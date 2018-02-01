package ml.milkov.mon.buffered.watcher

/** Watcher is responsible for running implementations of [[ml.milkov.mon.buffered.manager.MonitorManager]] */
trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
