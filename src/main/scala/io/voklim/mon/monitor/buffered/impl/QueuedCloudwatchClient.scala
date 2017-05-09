package io.voklim.mon.monitor.buffered.impl

import io.voklim.config.{CloudwatchConf, QueuedCloudwatchConf}
import io.voklim.mon.metrics.MetricKey
import io.voklim.mon.monitor.base.impl.CloudwatchClient
import io.voklim.mon.monitor.buffered.AbstractQueuedMonitor

class QueuedCloudwatchClient(conf: QueuedCloudwatchConf) extends AbstractQueuedMonitor(conf) {

  private val cc = new CloudwatchClient(conf)

  override def send(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): Unit = cc.send(metric, metrics:_*)
}
