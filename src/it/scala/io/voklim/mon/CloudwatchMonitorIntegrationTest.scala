package io.voklim.mon

import java.time.ZoneId
import java.util.Date
import java.time.LocalDate

import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest
import io.voklim.mon.config.CloudwatchConf
import io.voklim.mon.it._
import io.voklim.mon.monitor.base.impl.CloudwatchClient

import scala.concurrent.duration._
import scala.collection.JavaConverters._

/** Attempts to send a metric name and corresponding value to AWS and subsequently
  * read a sum aggregate of that metric over a period of time, ensuring that
  * the sum has been incremented */
class CloudwatchMonitorIntegrationTest extends ITPropertySpec{

  property("should increment test metric"){

    /** Create extended client that can read and write to CloudWatch */
    val client =  new DummyCloudWatchClient(testConf)

    /** Read the sum of the existing datapoints for this metric */
    val startLen = client.readMetric(metricTestKey.toKeyString)

    /** Increment the metric via an async write. */
    client.send((metricTestKey , 1))

    /** Ensure async send has enough time to reach AWS by polling reads every
      * 500 milliseconds with a 3 second timeout */
    pollExpr(500.milliseconds, 3.seconds) {
      val endLen = client.readMetric(metricTestKey.toKeyString)
      /** End sum should be 1 more than start sum */
      endLen shouldBe (startLen + 1)
    }
  }
}

class DummyCloudWatchClient(conf: CloudwatchConf) extends CloudwatchClient(conf){

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
    resp.getDatapoints().asScala.toList.headOption  match {
      case Some(d) => d.getSum().toInt
      case None => 0
    }
  }
}
