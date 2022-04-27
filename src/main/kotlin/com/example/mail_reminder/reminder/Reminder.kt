package com.example.mail_reminder.reminder

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Reminder(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0,
    val email: String,
    val description: String,
    val reminderCronTime: String
    ) {
}