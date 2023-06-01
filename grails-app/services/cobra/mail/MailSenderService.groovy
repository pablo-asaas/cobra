package cobra.mail

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email

class MailSenderService {

    private SendGrid sendGrid = new SendGrid(System.getenv("SENDGRID_API_KEY"))
    private Email senderEmail = new Email("SENDGRID_FROM_EMAIL_ADDRESS")

    public void send(String recipient, String subject, String contentBody) {
        Email recipientEmail = new Email(recipient)
        Content content = new Content("text/plain", contentBody)
        Mail mail = new Mail(senderEmail, subject, recipientEmail, content)

        try {
            Request request = new Request()

            request.method = Method.POST
            request.endpoint = "mail/send"
            request.body = mail.build()

            sendGrid.api(request)
        } catch (Exception exception) {
            exception.printStackTrace()
        }
    }
}
