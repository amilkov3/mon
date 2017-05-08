package io.voklim.mon.monitor.base

import io.voklim.mon.common._

/** Reports application metrics. */
trait Monitor {
  def send(metric: Metric, metrics: Metric*): Unit
}
