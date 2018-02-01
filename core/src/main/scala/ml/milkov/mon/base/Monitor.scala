package ml.milkov.mon.base

import ml.milkov.internal.common._

/** Reports application metrics. */
trait Monitor[F[_]] {
  def send(metric: Metric, metrics: Metric*): F[Unit]
}
