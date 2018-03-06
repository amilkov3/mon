package mon

trait CloudwatchExports {
  type CloudwatchConf = config.CloudwatchConf

  val CloudwatchTimerWatcher = buffered.watcher.CloudwatchTimerWatcher

  type CloudwatchClient[F[_], A <: metrickey.MetricK] = cloudwatch.impl.CloudwatchClient[F, A]
}
