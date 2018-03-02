package ml.milkov.mon.buffered.watcher.impl

import ml.milkov.mon.buffered.manager.impl.HashMapManager
import ml.milkov.test._
import ml.milkov.internal.common._
import ml.milkov.mon.buffered.QueueMetricBuffer
import ml.milkov.mon.buffered.watcher.execution.Sync

import scala.concurrent.duration._

/** Test to verify Java task MonitorWatcher is integrating soundly with MonitorManager */
class TimerMonitorWatcherTest extends UnitPropertySpec {

  property("monitor watcher should should run in background in conjunction with monitor manager") {

    val qmb = new QueueMetricBuffer[MetricKey](testConf)
    val hmm = new HashMapManager[Id, MetricKey](
      qmb,
      new DummyMonitor
    )

    val monitorWatcher = new TimerMonitorWatcher[Id](
      hmm,
      testConf,
      Sync
    )

    /** Populate queue in queued monitor */
    populatedQueue(qmb)

    /** Start monitor watcher */
    monitorWatcher.run()

    /** Verify that intermediate map is empty after [[testConf.sendMetricsInterval]] has passed  */
    pollExpr(
      testConf.sendMetricsInterval,
      testConf.sendMetricsInterval + 3.seconds
    ) {
      hmm.getMapSnapshot()(metricTestKey).length shouldBe 0
      hmm.getMapSnapshot()(aggTestKey).length shouldBe 1
    }
  }
}