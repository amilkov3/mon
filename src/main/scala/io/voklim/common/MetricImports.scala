package io.voklim.common

import io.voklim.mon.metrics.MetricKey

trait MetricImports {
  type Metric = (MetricKey, Double)
}
