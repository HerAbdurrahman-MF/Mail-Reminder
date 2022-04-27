package com.example.mail_reminder.reminder

import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ReminderService(
    private val repository: ReminderRepository
) {

    fun getReminderByEmail(email: String): List<Reminder> = repository.findByEmail(email).toList()

    fun getReminderById(id: Int): Reminder? = repository.findByIdOrNull(id)

    fun createReminder(reminder: Reminder): Reminder = repository.save(reminder)

    fun deleteReminderById(reminderId: Int): Pair<Boolean, Int?> {
        return if (repository.existsById(reminderId)) {
            val job = repository.findById(reminderId).get().job
            repository.deleteById(reminderId)
            Pair(true, job)
        } else Pair(false, null)
    }
    //fun deleteReminderById(reminderId: Int): Unit = repository.deleteById(reminderId)


    fun updateReminderById(reminderId: Int, reminder: Reminder): Pair<Reminder?, Int?> {
        val job = repository.findById(reminderId).get().job
        return if (repository.existsById(reminderId)) {
            Pair(repository.save(
                Reminder(
                    id = reminderId,
                    email = reminder.email,
                    description = reminder.description,
                    cronTime = reminder.cronTime
                )
            ), job)
        } else Pair(null, null)
    }
    //fun updateRminderById(reminderId: Int, reminder: Reminder): Reminder =
}