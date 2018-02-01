package ml.milkov


package object mon extends MonExports

trait MonExports {

  type TimerMonitorWatcher[F[_]] =  ml.milkov.mon.buffered.watcher.impl.TimerMonitorWatcher[F]
  type HashMapManager[F[_]] = ml.milkov.mon.buffered.manager.impl.HashMapManager[F]

  type BufferedMonitorConf = ml.milkov.config.BufferedMonitorConf
  type MonitorWatcherConf = ml.milkov.config.MonitorWatcherConf

  type MetricPrefix = ml.milkov.mon.metrics.MetricPrefix
  type MetricDomain = ml.milkov.mon.metrics.MetricDomain
  type MetricName = ml.milkov.mon.metrics.MetricName

  type MetricKey = ml.milkov.mon.metrics.MetricKey
  val MetricKey = ml.milkov.mon.metrics.MetricKey
}
