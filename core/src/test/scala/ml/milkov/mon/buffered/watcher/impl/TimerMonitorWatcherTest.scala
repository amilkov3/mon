package ml.milkov.mon.buffered.watcher.impl

import ml.milkov.mon.buffered.manager.impl.HashMapManager
import ml.milkov.test._
import ml.milkov.internal.common._

import scala.concurrent.duration._

/** Test to verify Java task MonitorWatcher is integrating soundly with MonitorManager */
/*class TimerMonitorWatcherTest extends UnitPropertySpec {

  property("monitor watcher should should run in background in conjunction with monitor manager") {

    val dummyQueuedMonitor = new DummyQueuedMonitorImpl(testConf)
    val hashMapManager = new HashMapManager[Id, MetricKey](dummyQueuedMonitor)

    val monitorWatcher = new TimerMonitorWatcher[Id](hashMapManager, testConf, Sync)

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
}*/