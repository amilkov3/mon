package ml.milkov.mon.base

import ml.milkov.internal.common._
import ml.milkov.mon.metrickey.MetricK

/** Interface for a raw metrics client */
abstract class Monitor[F[_]: Effect, A <: MetricK: Show] {

  /** Send metrics upstream */
  def send(metrics: Metric[A]*): F[Unit]
}
