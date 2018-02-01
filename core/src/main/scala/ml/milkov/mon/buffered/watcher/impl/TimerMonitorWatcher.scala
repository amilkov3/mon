package ml.milkov.mon.buffered.watcher.impl

import java.util.{Timer, TimerTask}

import cats.effect.IO
import ml.milkov.config.MonitorWatcherConf
import ml.milkov.mon.buffered.manager.MonitorManager
import ml.milkov.mon.buffered.watcher.MonitorWatcher
import ml.milkov.internal.common._

/** Sends metrics via [[MonitorManager]] in
  * a seperate thread at a configured interval */
class TimerMonitorWatcher[F[_]: Effect: Unsafe](
  monitorManager: MonitorManager[F],
  conf: MonitorWatcherConf,
  execStyle: ExecutionStyle
) extends {}
  with MonitorWatcher
  with Logging
{

  private val timer = new Timer()

  override def run(): Unit = {
    val task = new TimerTask {
      override def run(): Unit = {
        logger.info("MonitorWatcher dispatching metrics to be sent...")
        execStyle match {
          case Async => monitorManager.sendChunk().runAsync{
            case Left(err) => IO(logger.error(s"Sending metric(s) failed with: $err"))
            case _: Right[_,_] => IO.unit
          }.unsafeRunSync()
          case Sync => monitorManager.sendChunk().unsafePerformSync()
        }
      }
    }
    logger.info("Starting monitor watcher")
    timer.scheduleAtFixedRate(task, 0, conf.sendMetricsInterval.toMillis)
  }

  override def stop(): Unit = {
    logger.info("Stopping monitor watcher")
    timer.cancel()
  }
}

sealed trait ExecutionStyle
case object Async extends ExecutionStyle
case object Sync extends ExecutionStyle

