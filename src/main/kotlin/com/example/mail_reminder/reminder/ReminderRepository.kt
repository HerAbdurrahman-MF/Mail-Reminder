package com.example.mail_reminder.reminder

import org.springframework.data.repository.CrudRepository

interface ReminderRepository : CrudRepository<Reminder, Int> {
    fun findByEmail(email: String): Iterable<Reminder>
}