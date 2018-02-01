package ml.milkov.mon.cloudwatch

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import com.amazonaws.services.cloudwatch.model.{MetricDatum, PutMetricDataRequest}
import ml.milkov.config.CloudwatchConf
import ml.milkov.internal.common._
import ml.milkov.mon.base.Monitor
import monix.eval.Task

import scala.util.{Failure, Success}

class CloudwatchClient[F[_]: Effect](conf: CloudwatchConf) extends Monitor[F] with Logging {

  val client = new AmazonCloudWatchClient()

  private implicit val scheduler = defaultScheduler

  /** Async sends a series of metrics */
  override def send(metric: Metric, metrics: Metric*): F[Unit] = {
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

    /*Effect[F].async[Unit]{cb =>
      Task(client.putMetricData(r)).runAsync.onComplete[Unit]{
        case Failure(f) => cb(Left(f))
        case _ => cb(Right(()))
      }
    }*/

    Effect[F].delay(client.putMetricData(r))
  }
    /*.runAsync.onComplete[Unit]{
    case Failure(err) => logger.error(s"Sending metric(s) failed with: $err")
    case Success(_) => ()
  }*/
}
