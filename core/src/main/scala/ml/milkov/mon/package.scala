package ml.milkov

import ml.milkov.mon.effect.UnsafeOps
import ml.milkov.mon.metrickey
import ml.milkov.mon.metrickey.MetricK

package object mon extends CoreExports

trait CoreExports {

  type MetricBufferConf[A <: MetricK] = mon.config.MetricBufferConf[A]
  type MonitorWatcherConf = mon.config.MonitorWatcherConf

  type ExecutionStyle = mon.buffered.watcher.execution.ExecutionStyle
  val Async = mon.buffered.watcher.execution.Async
  val Sync = mon.buffered.watcher.execution.Sync

  type MetricKey = metrickey.MetricKey
  val MetricKey = metrickey.MetricKey
  type MetricPrefix = metrickey.MetricPrefix.Type
  val MetricPrefix = metrickey.MetricPrefix
  type MetricDomain = metrickey.MetricDomain.Type
  val MetricDomain = metrickey.MetricDomain
  type MetricName = metrickey.MetricName.Type
  val MetricName = metrickey.MetricName

  type MetricK = mon.metrickey.MetricK

  type Unsafe[F[_]] = mon.effect.Unsafe[F]
  val Unsafe = mon.effect.Unsafe

  implicit def toUnsafeOps[F[_]: Unsafe, A](x: F[A]): UnsafeOps[F, A] = new UnsafeOps[F, A](x)
}
