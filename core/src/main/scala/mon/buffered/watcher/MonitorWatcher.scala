package mon.buffered.watcher

/** Watcher is responsible for running implementations of
  * [[mon.buffered.manager.MonitorManager]] */
trait MonitorWatcher {
  def run(): Unit
  def stop(): Unit
}
