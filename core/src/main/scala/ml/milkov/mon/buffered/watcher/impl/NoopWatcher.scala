package ml.milkov.mon.buffered.watcher.impl

import ml.milkov.mon.buffered.watcher.MonitorWatcher

object NoopWatcher extends MonitorWatcher {

  override def run(): Unit = ()

  override def stop(): Unit = ()
}
