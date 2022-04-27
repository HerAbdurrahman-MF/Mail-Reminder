package com.example.mail_reminder.mail_sender

class SendMailTask(
    private val mailSenderService: MailSenderService,
    private val targetEmail: String,
    private val description: String
    ) : Runnable {

    override fun run() {
        val message =
            "We are sending you an email from the reminder you created with the description: \n \n$description"
        mailSenderService.sendEmail(
            subject = "Your reminder from mail reminder service",
            targetEmail = targetEmail,
            text = message
        )
    }
}