package cobra.notification

import cobra.customer.Customer
import cobra.payment.Payment
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class AlertNotificationService {

    @ReadOnly
    public List<AlertNotification> findAll(Customer customer) {
        return AlertNotification.query([customer          : customer,
                                        includeRead       : true,
                                        'createdAt[order]': 'desc']).list()
    }

    @ReadOnly
    public List<AlertNotification> findLatestUnread(Customer customer, Integer limit) {
        return AlertNotification.query([customer          : customer,
                                        limit             : limit,
                                        'createdAt[order]': 'desc']).list()
    }

    public AlertNotification findByPublicId(Customer customer, String publicId) {
        return AlertNotification.query([customer   : customer,
                                        publicId   : publicId,
                                        includeRead: true]).get()
    }

    public void save(Payment payment, AlertNotificationType type) {
        AlertNotification alertNotification = new AlertNotification()

        alertNotification.customer = payment.customer
        alertNotification.payment = payment
        alertNotification.type = type

        alertNotification.save(failOnError: true)
    }

    public void markAsRead(AlertNotification alertNotification) {
        if (!alertNotification.unread) return

        alertNotification.unread = false
        alertNotification.save(failOnError: true)
    }
}
