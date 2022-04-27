package com.example.mail_reminder.scheduler

import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.support.CronTrigger
import org.springframework.stereotype.Service
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.atomic.AtomicInteger

@Service
class SchedulerService (private val scheduler: TaskScheduler) {
    val futures: MutableMap<Int, ScheduledFuture<*>?> = HashMap()
    val taskId = AtomicInteger()

    fun addTask(task: Runnable, cronTime: String): Int {
        val cronTrigger = CronTrigger(cronTime)
        val scheduledTaskFuture = scheduler.schedule(task, cronTrigger)

        val id = taskId.incrementAndGet()
        futures[id] = scheduledTaskFuture

        return id
    }
}