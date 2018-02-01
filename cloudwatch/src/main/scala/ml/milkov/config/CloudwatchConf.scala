package ml.milkov.config

trait CloudwatchConf {
  def namespace: String
}

trait QueuedCloudwatchConf extends CloudwatchConf with BufferedMonitorConf
