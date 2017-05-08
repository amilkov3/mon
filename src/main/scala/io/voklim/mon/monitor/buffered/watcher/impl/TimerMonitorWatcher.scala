package io.voklim.mon.monitor.buffered.watcher.impl

import java.util.{Timer, TimerTask}

import io.voklim.mon.config.MonitorWatcherConf
import io.voklim.mon.monitor.buffered.manager.MonitorManager
import io.voklim.mon.monitor.buffered.watcher.MonitorWatcher
import io.voklim.mon.common._

/** Sends metrics via [[MonitorManager]] in
  * a seperate thread at a configured interval */
class TimerMonitorWatcher(
  monitorManager: MonitorManager,
  conf: MonitorWatcherConf
) extends {}
  with MonitorWatcher
  with Logging
{

  private val timer = new Timer()

  override def run(): Unit = {
    val task = new TimerTask {
      override def run(): Unit = {
        logger.info("MonitorWatcher dispatching metrics to be sent...")
        monitorManager.sendChunk()
      }
    }
    logger.info("Starting monitor watcher")
    timer.scheduleAtFixedRate(task, 0, conf.sendMetricsInterval.toMillis)
  }

  override def stop(): Unit = {
    logger.info("Stopping monitor watcher")
    timer.cancel()
  }
}
