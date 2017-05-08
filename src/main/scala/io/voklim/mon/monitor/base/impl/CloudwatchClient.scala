package io.voklim.mon.monitor.base.impl

import io.voklim.mon.config.CloudwatchConf
import io.voklim.mon.common._
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import com.amazonaws.services.cloudwatch.model.{MetricDatum, PutMetricDataRequest}
import io.voklim.mon.monitor.base.Monitor
import monix.eval.Task

import scala.util.{Failure, Success}

class CloudwatchClient(conf: CloudwatchConf) extends Monitor with Logging {

  val client = new AmazonCloudWatchClient()

  private implicit val scheduler = defaultScheduler

  /** Async sends a series of metrics */
  override def send(metric: Metric, metrics: Metric*): Unit = Task {
    val r = new PutMetricDataRequest()
    val mdL = new java.util.ArrayList[MetricDatum]()
    val metricSeq = (metric +: metrics)
    logger.info(s"Sending ${metricSeq.length} metrics to Cloudwatch")
    metricSeq.foreach { case (n, v) =>
      val md = new MetricDatum()
      md.setMetricName(n.toKeyString)
      md.setValue(v)
      mdL.add(md)
    }

    r.setMetricData(mdL)
    r.setNamespace(conf.namespace)

    client.putMetricData(r)
  }.runAsync.onComplete[Unit]{
    case Failure(err) => logger.error(s"Sending metric(s) failed with: $err")
    case Success(_) => ()
  }
}
