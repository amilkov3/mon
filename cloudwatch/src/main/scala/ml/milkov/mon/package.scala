package ml.milkov

import ml.milkov.mon.metrickey.MetricK

package object mon extends CloudwatchExports with CoreExports

trait CloudwatchExports {
  type CloudwatchConf = mon.config.CloudwatchConf

  val CloudwatchTimerWatcher = mon.buffered.watcher.CloudwatchTimerWatcher

  type CloudwatchClient[F[_], A <: MetricK] = mon.cloudwatch.CloudwatchClient[F, A]
}
