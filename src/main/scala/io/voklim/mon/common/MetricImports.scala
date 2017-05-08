package io.voklim.mon.common

import io.voklim.mon.metrics.MetricKey

trait MetricImports {
  type Metric = (MetricKey, Double)
}
