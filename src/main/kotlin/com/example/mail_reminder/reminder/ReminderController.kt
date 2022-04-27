package com.example.mail_reminder.reminder

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reminder")
class ReminderController(
    @Autowired
    private val reminderRepository: ReminderRepository
    ) {

    @GetMapping("/{email}")
    fun getReminder(@PathVariable("email") email : String): Iterable<Reminder> {
        return reminderRepository.findByEmail(email)
    }

    @PostMapping("/{email}/add")
    fun addNewReminder(@RequestBody reminder : Reminder): Reminder{
        reminderRepository.save(reminder)
        return reminder
    }
}