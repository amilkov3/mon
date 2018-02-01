package ml.milkov.mon.cloudwatch

import ml.milkov.config.QueuedCloudwatchConf
import ml.milkov.mon.metrics.MetricKey
import ml.milkov.mon.buffered.AbstractQueuedMonitor
import ml.milkov.internal.common._


class QueuedCloudwatchClient[F[_]: Effect](conf: QueuedCloudwatchConf) extends AbstractQueuedMonitor[F](conf) {

  private val cc = new CloudwatchClient[F](conf)

  override def send(metric: (MetricKey, Double), metrics: (MetricKey, Double)*): F[Unit] = cc.send(metric, metrics:_*)
}
