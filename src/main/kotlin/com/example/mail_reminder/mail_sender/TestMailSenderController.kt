package com.example.mail_reminder.mail_sender

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class TestMailSenderController(private val mailSenderService: MailSenderService) {

    @PostMapping("/send")
    fun sendMail(@RequestBody request: EmailRequest): ResponseEntity<Void> {
        mailSenderService.sendEmail(
            subject = request.subject,
            targetEmail = request.targetEmail,
            text = request.text
        )

        return ResponseEntity.noContent().build()
    }

    @GetMapping("/test")
    fun test(): String {
        return "hello"
    }
}