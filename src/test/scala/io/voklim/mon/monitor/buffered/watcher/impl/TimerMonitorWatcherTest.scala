package io.voklim.mon.monitor.buffered.watcher.impl

import io.voklim.mon.monitor.buffered.manager.impl.HashMapManager
import io.voklim.test._

import scala.concurrent.duration._

/** Test to verify Java task MonitorWatcher is integrating soundly with MonitorManager */
class TimerMonitorWatcherTest extends UnitPropertySpec {

  property("monitor watcher should should run in background in conjunction with monitor manager") {

    val dummyQueuedMonitor = new DummyQueuedMonitorImpl(testConf)
    val hashMapManager = new HashMapManager(dummyQueuedMonitor)

    val monitorWatcher = new TimerMonitorWatcher(hashMapManager, testConf)

    /** Populate queue in queued monitor */
    populatedQueue(dummyQueuedMonitor)

    /** Start monitor watcher */
    monitorWatcher.run()

    /** Verify that intermediate map is empty after [[testConf.sendMetricsInterval]] has passed  */
    pollExpr(
      testConf.sendMetricsInterval,
      testConf.sendMetricsInterval + 3.seconds
    ) {
      hashMapManager.getMapSnapshot().values.foreach{_ should be(0D)}
    }
  }
}