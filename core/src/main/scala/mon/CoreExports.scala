package mon

trait CoreExports {

  type MetricBufferConf[A <: MetricK] = mon.config.MetricBufferConf[A]
  type MonitorWatcherConf = mon.config.MonitorWatcherConf

  type ExecutionStyle = mon.buffered.watcher.execution.ExecutionStyle
  val Async = mon.buffered.watcher.execution.Async
  val Sync = mon.buffered.watcher.execution.Sync

  type MetricKey = mon.metrickey.MetricKey
  val MetricKey = mon.metrickey.MetricKey
  type MetricPrefix = mon.metrickey.MetricPrefix.Type
  val MetricPrefix = mon.metrickey.MetricPrefix
  type MetricDomain = mon.metrickey.MetricDomain.Type
  val MetricDomain = mon.metrickey.MetricDomain
  type MetricName = mon.metrickey.MetricName.Type
  val MetricName = mon.metrickey.MetricName

  type MetricK = mon.metrickey.MetricK

  type Unsafe[F[_]] = mon.effect.Unsafe[F]
  val Unsafe = mon.effect.Unsafe

  implicit def toUnsafeOps[F[_]: Unsafe, A](x: F[A]): mon.effect.UnsafeOps[F, A] = new mon.effect.UnsafeOps[F, A](x)
}
