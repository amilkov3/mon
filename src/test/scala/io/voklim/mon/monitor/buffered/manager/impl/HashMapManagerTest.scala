package io.voklim.mon.monitor.buffered.manager.impl

import org.scalatest.BeforeAndAfterEach

import io.voklim.test._

class HashMapManagerTest extends {}
  with UnitPropertySpec
  with BeforeAndAfterEach
{

  var dummyQueuedMonitor = new DummyQueuedMonitorImpl(testConf)

  /** Populate monitor queue before each test */
  override def beforeEach(): Unit = {
    populatedQueue(dummyQueuedMonitor)
  }

  property("hash map manager should managed queued buffer appropriately") {

    val hashMapManager = new HashMapManager(dummyQueuedMonitor)
    hashMapManager.sendChunk()

    hashMapManager.getMapSnapshot().values.foreach{_ should be(0D)}
  }

  property("should flush queued by configured quantity") {
    val vector = dummyQueuedMonitor.iterNextChunk()
    /** Plus one because we are also sending the number of metrics
      *  remaining in the queue as a metric itself */
    vector.length should be(testConf.flushMetricsCount + 1)
  }

  /** Reset queued monitor after each test */
  override def afterEach(): Unit = {
    dummyQueuedMonitor = new DummyQueuedMonitorImpl(testConf)
  }
}
