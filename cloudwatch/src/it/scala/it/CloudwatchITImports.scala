package it

import mon.config.CloudwatchConf
import mon.metrickey.MetricKey
import test.MonTestImports

import scala.concurrent.duration._

trait CloudwatchITImports extends MonTestImports {

  trait ITConf extends {}
    with mon.config.MetricBufferConf[MetricKey]
    with mon.config.MonitorWatcherConf
    with CloudwatchConf

  val itConf = new ITConf {
    override val flushMetricsCount: Int = 50
    override val namespace: String = "mon"
    override val sendMetricsInterval: FiniteDuration = 5.seconds
    override val bufferSizeMetricName: Option[MetricKey] = None
  }

}
