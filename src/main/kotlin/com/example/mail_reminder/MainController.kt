package com.example.mail_reminder

import com.example.mail_reminder.mail_sender.MailSenderService
import com.example.mail_reminder.mail_sender.SendMailTask
import com.example.mail_reminder.reminder.Reminder
import com.example.mail_reminder.reminder.ReminderRepository
import com.example.mail_reminder.reminder.ReminderService
import com.example.mail_reminder.scheduler.SchedulerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reminder")
class MainController(
    @Autowired
    private val reminderRepository: ReminderRepository,
    private val reminderService: ReminderService,
    private val mailSenderService: MailSenderService,
    private val schedulerService: SchedulerService
    ) {

    @GetMapping("/old/{email}")
    fun getReminder(@PathVariable("email") email : String): Iterable<Reminder> {
        return reminderRepository.findByEmail(email)
    }

    @PostMapping("/old/{email}/add")
    fun addNewReminder(@RequestBody reminder : Reminder): Reminder {
        val mailTask = SendMailTask(
            mailSenderService = mailSenderService,
            targetEmail = reminder.email,
            description = reminder.description,
            cronTime = reminder.cronTime
        )
        val id = schedulerService.addTask(mailTask, reminder.cronTime)
        reminder.job = id

        reminderRepository.save(reminder)
        return reminder
    }

    @GetMapping("/new/{email}")
    fun getReminderByEmail(@PathVariable("email") email : String): ResponseEntity<List<Reminder>> {
        return ResponseEntity<List<Reminder>>(reminderService.getReminderByEmail(email), HttpStatus.OK)
    }

    @GetMapping("/new/{email}/{id}")
    fun getReminderById(@PathVariable("id") id : Int): ResponseEntity<Reminder> {
        val reminder = reminderService.getReminderById(id)
        return if (reminder != null) {
            ResponseEntity<Reminder>(reminder, HttpStatus.OK)
        } else {
            ResponseEntity<Reminder>(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/new/{email}/add")
    fun createReminder(@RequestBody payload: Reminder): ResponseEntity<Reminder> {
        val mailTask = SendMailTask(
            mailSenderService = mailSenderService,
            targetEmail = payload.email,
            description = payload.description,
            cronTime = payload.cronTime
        )
        val id = schedulerService.addTask(mailTask, payload.cronTime)
        payload.job = id

        val reminder = reminderService.createReminder(payload)
        return if (reminder != null) {
            ResponseEntity<Reminder>(reminder, HttpStatus.OK)
        } else {
            ResponseEntity<Reminder>(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping("/new/{email}/{id}/delete")
    fun deleteReminderById(@PathVariable("id") id: Int): ResponseEntity<Unit> {
        val (isSuccess, job) = reminderService.deleteReminderById(id)
        return if (isSuccess) {
            schedulerService.futures[job]?.cancel(false)
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/new/{email}/{id}/update")
    fun updateReminderById(@PathVariable("id") id: Int, @RequestBody payload: Reminder): ResponseEntity<Reminder> {
        val (reminder, job) = reminderService.updateReminderById(id, payload)
        return if (reminder != null) {
            val mailTask = SendMailTask(
                mailSenderService = mailSenderService,
                targetEmail = payload.email,
                description = payload.description,
                cronTime = payload.cronTime
            )
            val id = schedulerService.addTask(mailTask, payload.cronTime)
            payload.job = id

            schedulerService.futures[job]?.cancel(false)

            ResponseEntity<Reminder>(reminder, HttpStatus.OK)
        } else {
            ResponseEntity<Reminder>(HttpStatus.NOT_FOUND)
        }
    }
}