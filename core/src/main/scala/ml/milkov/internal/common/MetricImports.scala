package ml.milkov.internal.common

import ml.milkov.mon.metrics.MetricKey

trait MetricImports {
  type Metric = (MetricKey, Double)
}
