package com.example.mail_reminder.scheduler

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/scheduler")
class TestSchedulerController(private val scheduler: SchedulerService) {

    @PostMapping("/add")
    fun scheduleTask(){
        val str = "0 23 18 27 * *"
        scheduler.addTask(TestSchedulerTask(str), str)
        println(scheduler.futures)
    }
}

class TestSchedulerTask(val text: String): Runnable{
    override fun run() {
        println("=================")
        println(text)
        println(LocalDateTime.now())
        println("=================")
    }

}