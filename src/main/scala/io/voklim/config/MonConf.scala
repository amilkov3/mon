package io.voklim.config

import scala.concurrent.duration.FiniteDuration

trait BufferedMonitorConf {
  def flushMetricsCount: Int
}

trait MonitorWatcherConf {
  def sendMetricsInterval: FiniteDuration
}

trait CloudwatchConf {
  def namespace: String
}

trait QueuedCloudwatchConf extends CloudwatchConf with BufferedMonitorConf
