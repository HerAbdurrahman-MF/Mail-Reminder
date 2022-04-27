package com.example.mail_reminder

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MailReminderApplication

fun main(args: Array<String>) {
	runApplication<MailReminderApplication>(*args)
}
