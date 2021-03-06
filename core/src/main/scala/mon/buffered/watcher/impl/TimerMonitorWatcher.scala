package mon.buffered.watcher
package impl

import java.util.{Timer, TimerTask}

import cats.effect.IO
import mon.config.MonitorWatcherConf
import mon.buffered.manager.MonitorManager
import mon.internal.common._
import mon.buffered.watcher.execution._

/** Sends metrics via [[mon.buffered.manager.MonitorManager]] in
  * a seperate thread at a configured interval */
final class TimerMonitorWatcher[F[_]: Effect: Unsafe](
  monitorManager: MonitorManager[F],
  conf: MonitorWatcherConf,
  execStyle: ExecutionStyle
) extends {}
  with MonitorWatcher
  with Logging
{

  private val timer = new Timer()

  def run1(): Unit = {
    val task = new TimerTask {
      override def run(): Unit = {
        logger.info("MonitorWatcher dispatching metrics to be sent...")
        val failPrefix = s"Sending metric(s) failed with: "
        execStyle match {
          case Async => monitorManager.sendChunk().runAsync{
            case Left(err) => IO(logger.error(s"$failPrefix$err"))
            case _: Right[_,_] => IO.unit
          }.unsafeRunSync()
          case Sync => monitorManager.sendChunk().unsafeAttempt().fold(
            err => logger.error(s"$failPrefix$err"),
            _ => ()
          )
        }
      }
    }
    logger.info("Starting monitor watcher")
    timer.scheduleAtFixedRate(task, 0, conf.sendMetricsInterval.toMillis)
  }

  /** If [[Async]], will cut another thread to send metrics, otherwise will happen
    * on current thread */
  override def run(): Unit = {
    val task = new TimerTask {
      override def run(): Unit = {
        logger.info("MonitorWatcher dispatching metrics to be sent...")
        val failPrefix = s"Sending metric(s) failed with: "
        execStyle match {
          case Async => monitorManager.sendChunk().runAsync{
            case Left(err) => IO(logger.error(s"$failPrefix$err"))
            case _: Right[_,_] => IO.unit
          }.unsafeRunSync()
          case Sync => monitorManager.sendChunk().unsafeAttempt().fold(
            err => logger.error(s"$failPrefix$err"),
            _ => ()
          )
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


