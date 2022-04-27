package com.example.mail_reminder.mail_sender

data class EmailRequest(
    val subject: String,
    val targetEmail: String,
    val text: String
)
