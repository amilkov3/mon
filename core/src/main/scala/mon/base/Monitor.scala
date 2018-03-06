package mon.base

import mon.internal.common._
import mon.metrickey.MetricK

/** Interface for a raw metrics client */
abstract class Monitor[F[_]: Effect, A <: MetricK: Show] {

  /** Send metrics upstream */
  def send(metrics: Metric[A]*): F[Unit]
}
