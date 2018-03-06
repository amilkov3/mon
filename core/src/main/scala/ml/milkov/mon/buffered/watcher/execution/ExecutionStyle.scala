package ml.milkov.mon.buffered.watcher
package execution

/** ADT for how `MonitorWatcher` should send metrics */
sealed trait ExecutionStyle
case object Async extends ExecutionStyle
case object Sync extends ExecutionStyle
