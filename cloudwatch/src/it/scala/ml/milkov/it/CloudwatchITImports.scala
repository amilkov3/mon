package ml.milkov.it

import ml.milkov.mon.config.CloudwatchConf
import ml.milkov.mon.metrickey.MetricKey
import ml.milkov.test.MonTestImports

import scala.concurrent.duration._

trait CloudwatchITImports extends MonTestImports {

  trait ITConf extends {}
    with ml.milkov.mon.config.MetricBufferConf[MetricKey]
    with ml.milkov.mon.config.MonitorWatcherConf
    with CloudwatchConf

  val itConf = new ITConf {
    override val flushMetricsCount: Int = 50
    override val namespace: String = "mon"
    override val sendMetricsInterval: FiniteDuration = 5.seconds
    override val bufferSizeMetricName: Option[MetricKey] = None
  }

}
