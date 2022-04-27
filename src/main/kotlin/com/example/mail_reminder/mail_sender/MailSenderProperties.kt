package com.example.mail_reminder.mail_sender

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties
data class MailSenderProperties (
    val host: String = "smtp.gmail.com",
    val port: Int = 587,
    val username: String = System.getenv("TEST_MAIL"),
    val password: String = System.getenv("TEST_MAIL_PASS"),
    val protocol: String = "smtp",
    val auth: Boolean = true,
    val starttlsEnable: Boolean = true,
    val debug: Boolean = true
)