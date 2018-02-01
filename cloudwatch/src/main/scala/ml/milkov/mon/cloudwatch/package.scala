package ml.milkov.mon

package object cloudwatch

trait CloudwatchImports {
  type CloudwatchConf = ml.milkov.config.CloudwatchConf
  type QueuedCloudwatchConf = ml.milkov.config.QueuedCloudwatchConf

  type CloudwatchClient[F[_]] = cloudwatch.CloudwatchClient[F]
  type QueuedCloudwatchClient[F[_]] =  cloudwatch.QueuedCloudwatchClient[F]
}
