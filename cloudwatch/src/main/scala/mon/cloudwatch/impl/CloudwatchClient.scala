package mon.cloudwatch.impl

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import com.amazonaws.services.cloudwatch.model.{MetricDatum, PutMetricDataRequest}
import mon.base.Monitor
import mon.config.CloudwatchConf
import mon.internal.common._
import mon.metrickey.MetricK

/** Effect abstracted wrapper around Java client */
class CloudwatchClient[F[_]: Effect, A <: MetricK: Show](
  conf: CloudwatchConf
) extends Monitor[F, A] with Logging {

  val client = new AmazonCloudWatchClient()

  override def send(metrics: Metric[A]*): F[Unit] = {
    val r = new PutMetricDataRequest()
    val mdL = new java.util.ArrayList[MetricDatum]()
    logger.info(s"Sending ${metrics.length} metrics to Cloudwatch")
    metrics.foreach { case (n, t, v) =>
      val md = new MetricDatum()
      md.setMetricName(Show[A].show(n))
      md.setTimestamp(Timestamplike[java.util.Date].fromTimestamp(t))
      md.setValue(v)
      mdL.add(md)
    }

    r.setMetricData(mdL)
    r.setNamespace(conf.namespace)

    Effect[F].delay(client.putMetricData(r))
  }
}
