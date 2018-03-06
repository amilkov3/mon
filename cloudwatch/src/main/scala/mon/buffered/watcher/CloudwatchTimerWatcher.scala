package mon.buffered.watcher

import mon.config.{CloudwatchConf, MetricBufferConf, MonitorWatcherConf}
import mon.buffered.watcher.impl.TimerMonitorWatcher
import mon.internal.common._
import mon.base.Monitor
import mon.buffered.{MetricBuffer, QueueMetricBuffer}
import mon.buffered.manager.impl.HashMapManager
import mon.buffered.watcher.execution.{Async, ExecutionStyle}
import mon.cloudwatch.impl.CloudwatchClient

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
