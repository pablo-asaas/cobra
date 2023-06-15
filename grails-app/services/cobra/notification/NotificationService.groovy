package cobra.notification

import cobra.customer.Customer
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class NotificationService {

    @ReadOnly
    public List<Notification> findAll(Customer customer) {
        return Notification.query([customer: customer,
                                   includeRead: true,
                                   'createdAt[order]': 'desc']).list()
    }

    @ReadOnly
    public List<Notification> findLatestUnread(Customer customer, Integer limit) {
        return Notification.query([customer: customer,
                                   limit: limit,
                                   'createdAt[order]': 'desc']).list()
    }

    public Notification findByPublicId(Customer customer, String publicId) {
        return Notification.query([customer: customer,
                                   publicId: publicId,
                                   includeRead: true]).get()
    }

    public void markAsRead(Notification notification) {
        if (!notification.unread) return

        notification.unread = false
        notification.save(failOnError: true)
    }
}
