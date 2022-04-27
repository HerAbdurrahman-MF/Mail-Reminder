package com.example.mail_reminder

import com.example.mail_reminder.mail_sender.MailSenderService
import com.example.mail_reminder.mail_sender.SendMailTask
import com.example.mail_reminder.reminder.Reminder
import com.example.mail_reminder.reminder.ReminderRepository
import com.example.mail_reminder.scheduler.SchedulerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reminder")
class MainController(
    @Autowired
    private val reminderRepository: ReminderRepository,
    private val mailSenderService: MailSenderService,
    private val scheduler: SchedulerService
    ) {

    @GetMapping("/{email}")
    fun getReminder(@PathVariable("email") email : String): Iterable<Reminder> {
        return reminderRepository.findByEmail(email)
    }

    @PostMapping("/{email}/add")
    fun addNewReminder(@RequestBody reminder : Reminder): Reminder {
        val mailTask = SendMailTask(
            mailSenderService = mailSenderService,
            targetEmail = reminder.email,
            description = reminder.description
        )
        val id = scheduler.addTask(mailTask, reminder.cronTime)
        reminder.job = id

        reminderRepository.save(reminder)
        return reminder
    }
}