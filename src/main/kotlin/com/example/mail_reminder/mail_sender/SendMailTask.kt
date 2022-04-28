package com.example.mail_reminder.mail_sender

class SendMailTask(
    private val mailSenderService: MailSenderService,
    private val targetEmail: String,
    private val description: String,
    private val cronTime: String
    ) : Runnable {

    override fun run() {
        val message =
            "We are sending you an email from the reminder you created with the description: \n \n$description"
        val time = cronTime.split(" ")
        val humanReadableTime = "month:" + time[4] + ", date:" +time[3] + ", " + time[2] + ":" + time[1] + ":" + time[0]

        mailSenderService.sendEmail(
            subject = "Your reminder from mail reminder service for $humanReadableTime",
            targetEmail = targetEmail,
            text = message
        )
        println("mail sent")
    }
}