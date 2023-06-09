package cobra.payment

import cobra.mail.MailSenderService
import cobra.notification.AlertNotificationService
import cobra.notification.AlertNotificationType
import cobra.util.CurrencyUtils
import grails.gorm.transactions.Transactional

@Transactional
class PaymentNotificationService {

    AlertNotificationService alertNotificationService
    MailSenderService mailSenderService

    public void onSave(Payment payment) {
        String paymentValue = CurrencyUtils.format(payment.value)

        mailSenderService.send(payment.customer.email,
                        "Cobrança criada",
                    "Foi criada uma cobrança (${payment.publicId}) no valor de R\$ ${paymentValue} para ${payment.payer.name}")

        mailSenderService.send(payment.payer.email,
                        "Cobrança recebida",
                    "Você recebeu uma cobrança (${payment.publicId}) no valor de R\$ ${paymentValue}, enviada por ${payment.customer.name}")
    }

    public void onUpdate(Payment payment) {
        String subject = "Cobrança alterada"
        String contentBody = "A cobrança ${payment.publicId} foi alterada"

        mailSenderService.send(payment.customer.email, subject, contentBody)
        mailSenderService.send(payment.payer.email, subject, contentBody)
    }

    public void onDelete(Payment payment) {
        String subject = "Cobrança excluída"
        String contentBody = "A cobrança ${payment.publicId} foi excluída"

        mailSenderService.send(payment.customer.email, subject, contentBody)
        mailSenderService.send(payment.payer.email, subject, contentBody)
    }

    public void onRestore(Payment payment) {
        String subject = "Cobrança restaurada"
        String contentBody = "A cobrança ${payment.publicId} foi restaurada"

        mailSenderService.send(payment.customer.email, subject, contentBody)
        mailSenderService.send(payment.payer.email, subject, contentBody)
    }

    public void onOverdue(Payment payment) {
        String subject = "Cobrança vencida"
        String contentBody = "A cobrança ${payment.publicId} está vencida"

        mailSenderService.send(payment.customer.email, subject, contentBody)
        mailSenderService.send(payment.payer.email, subject, contentBody)

        alertNotificationService.save(payment, AlertNotificationType.PAYMENT_OVERDUE)
    }

    public void onPaid(Payment payment) {
        String subject = "Cobrança paga"
        String contentBody = "A cobrança ${payment.publicId} foi paga"

        mailSenderService.send(payment.customer.email, subject, contentBody)
        mailSenderService.send(payment.payer.email, subject, contentBody)

        alertNotificationService.save(payment, AlertNotificationType.PAYMENT_PAID)
    }
}
