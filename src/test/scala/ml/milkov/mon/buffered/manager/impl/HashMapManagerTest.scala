package ml.milkov.mon.buffered.manager.impl

import org.scalatest.BeforeAndAfterEach
import ml.milkov.test._
import ml.milkov.internal.common._
import ml.milkov.mon.buffered.QueueMetricBuffer

class HashMapManagerTest extends {}
  with UnitPropertySpec
  with BeforeAndAfterEach
{

  var qmb = new QueueMetricBuffer[MetricKey](testConf)

  /** Populate monitor queue before each test */
  override def beforeEach(): Unit = {
    populatedQueue(qmb)
  }

  property(
    "`sendChunk` should clear map value for test key and keep one agg value for agg key"
  ) {

    val hashMapManager = new HashMapManager[Id, MetricKey](
      qmb,
      new DummyMonitor
    )

    hashMapManager.sendChunk()

    hashMapManager.getMapSnapshot()(metricTestKey).length shouldBe 0
    hashMapManager.getMapSnapshot()(aggTestKey).head._2 shouldBe
      (testConf.flushMetricsCount / 2).toDouble
  }

  property("should flush queued by configured quantity") {
    val vector = qmb.iterNextChunk()
    vector.length should be(testConf.flushMetricsCount)
  }

  /** Reset queued monitor after each test */
  override def afterEach(): Unit = {
    qmb = new QueueMetricBuffer[MetricKey](testConf)
  }
}
