package io.voklim

package object mon extends MonExports

trait MonExports {

  type CloudwatchClient = io.voklim.mon.monitor.base.impl.CloudwatchClient
  type QueuedCloudwatchClient =  io.voklim.mon.monitor.buffered.impl.QueuedCloudwatchClient

  type TimerMonitorWatcher =  io.voklim.mon.monitor.buffered.watcher.impl.TimerMonitorWatcher
  type HashMapManager = io.voklim.mon.monitor.buffered.manager.impl.HashMapManager

  type CloudwatchConf = io.voklim.config.CloudwatchConf
  type QueuedCloudwatchConf = io.voklim.config.QueuedCloudwatchConf
  type BufferedMonitorConf = io.voklim.config.BufferedMonitorConf
  type MonitorWatcherConf = io.voklim.config.MonitorWatcherConf

  type MetricPrefix = io.voklim.mon.metrics.MetricPrefix
  type MetricDomain = io.voklim.mon.metrics.MetricDomain
  type MetricName = io.voklim.mon.metrics.MetricName

  type MetricKey = io.voklim.mon.metrics.MetricKey
  val MetricKey = io.voklim.mon.metrics.MetricKey
}
