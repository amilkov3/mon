
## Mon

Metrics management and associated metric client implementations

Supported clients:
* AWS Cloudwatch

Supported metric architectures:
* A queue you can push metrics to along with an associated hash map manager that is responsible for flushing a configurable quantity of metrics from the queue, updating the state of said metrics in an intermediate map (useful for cumulative metrics), and sending the metrics via a given client. A monitor watcher (implemented via a simple Java TimerTask) is responsible for periodically invoking the manager to coordinate the above steps

### Usage

```scala

import io.voklim.mon._

// Create a name for your metric
val metricKey = MetricKey.createKey(
  new MetricPrefix("testPrefix"),
  new MetricDomain("testDomain"),
  new MetricName("testName")
)

// A Cloudwatch conf
val ccConf = new CloudwatchConf {
  override val namespace = "myNamespace"
}

// And your client
val cc = new CloudwatchClient(ccConf)
// You can call send directly which will async send whatever metrics you provide
cc.send((metricKey, 1.00), (metricKey. 1.50))


//To use the manager watcher pattern do a:

import scala.concurrent.duration._

val mm = new HashMapManager(cc)

val mwConf = new MonitorWatcherConf {
  override val sendMetricsInterval = 2.minutes
}

val mw = new TimerMonitorWatcher(mm, mwConf)
mw.run()

```

