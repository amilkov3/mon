
## Mon

[![Build status](https://img.shields.io/travis/amilkov3/mon/master.svg)](https://travis-ci.org/amilkov3/mon)
[![codecov](https://codecov.io/gh/amilkov3/mon/branch/master/graph/badge.svg)](https://codecov.io/gh/amilkov3/mon)

![alt](mon.jpg)

Metrics management and associated metric client implementations

Supported clients:
* AWS Cloudwatch

Supported metric architectures:
* When you send metrics they are placed in an in-memory concurrent queue. 
A watcher (`java.util.Timer` task) sends a configured quantity of metrics to the upstream service 
at a configured interval. This is facilitated by a hash map manager, which drains the queue and 
places the metrics in an intermediate hashmap which is necessary in order to track aggregate 
metrics (more about this below). All of this happens on a single long running thread (the timer 
task) so there are no race conditions 

Say you send the following metrics:

```
("ametric", aggregate=false, timestamp1, 1.0), ("ametric", aggregate=false, timestamp2, 2.0),
("bmetric", aggregate=true, timestamp3, 1.0), (("bmetric", aggregate=true, timestamp4, 2.0),
("bmetric", aggregate=true, timestamp5, -1.0)
```

These will go in the hashmap as:

```
"ametric" -> [(timestamp1, 1.0), (timestamp2, 2.0)]
"bmetric" -> [(timestamp3, 1.0), (timestamp4, 3.0), (timestamp5, 2.0)]
```

And will be subsequently sent upstream as such

### Usage

Every metric key must extend `ml.milkov.mon.metrickey.MetricK` and have
an instance of `cats.Show`. There is a default key type: `ml.milkov.mon.metrickey.MetricKey`
if you want to just use that. It should be more than sufficient for most use cases

Here's how to instantiate a watcher and start sending metrics:

```scala

import ml.milkov.mon._

/** instantiate a metric */
val metricKey = MetricKey.createKey(
  MetricPrefix.tag("testPrefix"),
  MetricDomain.tag("testDomain"),
  MetricName.tag("testName", aggregate = true)
)

/** your Cloudwatch conf */
val cwConf = new CloudwatchConf {
  override val namespace = "myNamespace"
}

import cats.effect.IO

/** You can use just the client wrapper */
val cc = new CloudwatchClient[IO, MetricKey](cwConf)

/** You can call send directly which will send whatever metrics you provide sync or async
(depending on how you run the `cats.effect.Effect` instance) */
cc.send(
  (metricKey, Timestamp.now(), 1.0),
  (metricKey, Timestamp.now(), 2.0)
).unsafeRunSync()


//To use the full architecture

import scala.concurrent.duration._

val buffConf = new MetricBufferConf[MetricKey]{
  override val flushMetricsCount: Int = 50
  override def bufferSizeMetricName: Option[MetricKey] = None
}

val mwConf = new MonitorWatcherConf {
  override val sendMetricsInterval = 2.minutes
}

/** spin up architecture (`client` here is simply a convenience
* if you want to make raw metrics pushes)
 *  */
val (monitorWatcher, client, buffer) = CloudwatchTimerWatcher[IO, MetricKey](
  mwConf,
  buffConf,
  cwConf,
  Async // execution style for sending the metrics upstream, either Sync or Async
)

/** start the watcher */
monitorWatcher.run()

/** push metrics to the queue which will eventually
* be sent upstream to your metrics service by the watcher */
buffer.push(
  (metricKey, Timestamp.now(), 1.0),
  (metricKey, Timestamp.now(), 2.0)
)

```

