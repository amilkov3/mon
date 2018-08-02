package mon.buffered.watcher

/** Watcher is responsible for running implementations of
  * [[mon.buffered.manager.MonitorManager]] */
trait MonitorWatcher[F[_]] {
  def run(): F[Unit]
  def stop(): F[Unit]
}
