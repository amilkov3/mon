package ml.milkov.internal.common

import monix.execution.Scheduler

import scala.concurrent.ExecutionContextExecutor

trait ExecutionImports {

  val defaultExecutionContext: ExecutionContextExecutor = scala.concurrent.ExecutionContext.global

  val defaultScheduler: Scheduler = monix.execution.Scheduler.global
}
