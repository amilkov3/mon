package ml.milkov.mon.cloudwatch

import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest
import ml.milkov.mon.config.CloudwatchConf
import ml.milkov.it._
import ml.milkov.internal.common._
import java.time.{LocalDate, ZoneId}
import java.util.Date


import scala.collection.JavaConverters._
import scala.concurrent.duration._


/** Attempts to send a metric name and corresponding value to AWS and subsequently
  * read a sum aggregate of that metric over a period of time, ensuring that
  * the sum has been incremented */
class CloudwatchMonitorIntegrationTest extends ITPropertySpec{

  property("should increment test metric"){

    /** Create extended client that can read and write to CloudWatch */
    val client =  new DummyCloudWatchClient(itConf)

    /** Read the sum of the existing datapoints for this metric */
    val startLen = client.readMetric(metricTestKey.toKeyString)

    /** Increment the metric via an async write. */
    client.send((metricTestKey, Timestamp.now(), 1))

    /** Ensure async send has enough time to reach AWS by polling reads every
      * 500 milliseconds with a 3 second timeout */
    pollExpr(500.milliseconds, 3.seconds) {
      val endLen = client.readMetric(metricTestKey.toKeyString)
      /** End sum should be 1 more than start sum */
      endLen shouldBe (startLen + 1)
    }
  }
}

class DummyCloudWatchClient(
  conf: CloudwatchConf
) extends CloudwatchClient[Id, MetricKey](conf){

  def readMetric(metricName: String) = {

    val ss = new java.util.ArrayList[String]()
    ss.add("Sum")

    val g = new GetMetricStatisticsRequest()
    g.setStatistics(ss)
    g.setStartTime(
      Date.from(
        LocalDate.now()
          .atStartOfDay(ZoneId.systemDefault())
          .toInstant()
      )
    )
    g.setEndTime(
      Date.from(
        LocalDate.now().plusDays(1)
          .atStartOfDay(ZoneId.systemDefault())
          .toInstant()
      )
    )
    /** Denotes the granularity of the returned endpoints. In this case,
      * the endpoints will be returned in blocks of 60000000 second durations */
    g.setPeriod(60000000)
    g.setNamespace(conf.namespace)
    g.setMetricName(metricName)

    val resp = client.getMetricStatistics(g)
    resp.getDatapoints().asScala.toList.headOption.cata(_.getSum().toInt, 0)
  }
}
