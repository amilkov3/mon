package mon.internal.common

import mon.metrickey

package object data

trait DataImports {
  type Timestamp = data.Timestamp.Type
  val Timestamp = data.Timestamp

  type Metric[A <: MetricK] = (A, Timestamp, Double)

  type MetricK = metrickey.MetricK

  type MetricKey = metrickey.MetricKey
  val MetricKey = metrickey.MetricKey
  type MetricPrefix = metrickey.MetricPrefix.Type
  val MetricPrefix = metrickey.MetricPrefix
  type MetricDomain = metrickey.MetricDomain.Type
  val MetricDomain = metrickey.MetricDomain
  type MetricName = metrickey.MetricName.Type
  val MetricName = metrickey.MetricName

  type Timestamplike[A] = data.Timestamplike[A]
  val Timestamplike = data.Timestamplike
}
