package io.voklim.mon.monitor.buffered.watcher.impl

import io.voklim.mon.monitor.buffered.watcher.MonitorWatcher

object NoopWatcher extends MonitorWatcher {

  override def run(): Unit = ()
  
  override def stop(): Unit = ()
}
