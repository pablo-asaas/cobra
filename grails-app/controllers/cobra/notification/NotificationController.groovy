package cobra.notification

import cobra.base.BaseController
import cobra.util.CurrencyUtils
import cobra.util.DateUtils
import cobra.util.MessageUtils
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class NotificationController extends BaseController {

    NotificationService notificationService

    static allowedMethods = [index: 'GET', show: 'GET', navbarTrayLatestNotifications: 'GET']

    def index() {
        return [notificationList: notificationService.findAll(getCurrentCustomer())]
    }

    def show() {
        Notification notification = notificationService.findByPublicId(getCurrentCustomer(), params.id)

        if (notification.unread) {
            notificationService.markAsRead(notification)
        }

        if (notification.type == NotificationType.PAYMENT_PAID) {
            redirect(controller: 'receipt', action: 'show', id: notification.payment.publicId)
            return
        } else if (notification.type == NotificationType.PAYMENT_OVERDUE) {
            redirect(controller: 'payment', action: 'show', id: notification.payment.id)
            return
        }

        redirect(action: 'index')
    }

    def navbarTrayLatestNotifications() {
        Integer defaultNotificationLimit = 5

        List<Notification> notificationList = notificationService.findLatestUnread(getCurrentCustomer(), defaultNotificationLimit)
        List<Map> parsedNotificationList = []

        for (Notification notification : notificationList) {
            String messageCode = "NotificationType.${notification.type}"

            parsedNotificationList.add([
                    publicId: notification.publicId,
                    title: MessageUtils.getMessage("${messageCode}.title", null),
                    content: MessageUtils.getMessage("${messageCode}.content", [notification.payment.payer.name,
                                                                                            CurrencyUtils.format(notification.payment.value)]),
                    date: DateUtils.formatWithTime(notification.createdAt)])
        }

        render(parsedNotificationList as JSON, status: HttpStatus.OK.code)
    }
}
