package ml.milkov.mon.buffered.watcher

import ml.milkov.mon.config.{CloudwatchConf, MetricBufferConf, MonitorWatcherConf}
import ml.milkov.mon.buffered.watcher.impl.TimerMonitorWatcher
import ml.milkov.internal.common._
import ml.milkov.mon.base.Monitor
import ml.milkov.mon.buffered.{MetricBuffer, QueueMetricBuffer}
import ml.milkov.mon.buffered.manager.impl.HashMapManager
import ml.milkov.mon.buffered.watcher.execution.{Async, ExecutionStyle}
import ml.milkov.mon.cloudwatch.CloudwatchClient

/** Singleton to spin up the entire metric architecture backed by the Cloudwatch client */
object CloudwatchTimerWatcher {
  def apply[F[_]: Effect: Unsafe, A <: MetricK: Show](
    mwConf: MonitorWatcherConf,
    buffConf: MetricBufferConf[A],
    cwConf: CloudwatchConf,
    execStyle: ExecutionStyle = Async
  ): (MonitorWatcher, Monitor[F, A], MetricBuffer[A]) = {
    val buff = new QueueMetricBuffer[A](buffConf)
    val cc = new CloudwatchClient[F, A](cwConf)
    (
      new TimerMonitorWatcher[F](
        new HashMapManager[F, A](buff, cc),
        mwConf,
        execStyle
      ),
      cc,
      buff
    )
  }
}
