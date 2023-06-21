package cobra.notification

import cobra.base.BaseController
import cobra.util.CurrencyUtils
import cobra.util.DateUtils
import cobra.util.MessageUtils
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class AlertNotificationController extends BaseController {

    AlertNotificationService alertNotificationService

    static allowedMethods = [index: 'GET', show: 'GET', navbarTrayLatestNotifications: 'GET']

    def index() {
        return [alertNotificationList: alertNotificationService.findAll(getCurrentCustomer())]
    }

    def show() {
        AlertNotification alertNotification = alertNotificationService.findByPublicId(getCurrentCustomer(), params.id)

        if (alertNotification.unread) {
            alertNotificationService.markAsRead(alertNotification)
        }

        if (alertNotification.type == AlertNotificationType.PAYMENT_PAID) {
            redirect(controller: 'receipt', action: 'show', id: alertNotification.payment.publicId)
            return
        } else if (alertNotification.type == AlertNotificationType.PAYMENT_OVERDUE) {
            redirect(controller: 'payment', action: 'show', id: alertNotification.payment.id)
            return
        }

        redirect(action: 'index')
    }

    def navbarTrayLatestNotifications() {
        Integer defaultNotificationLimit = 5

        List<AlertNotification> alertNotificationList = alertNotificationService.findLatestUnread(getCurrentCustomer(), defaultNotificationLimit)
        List<Map> parsedAlertNotificationList = []

        for (AlertNotification alertNotification : alertNotificationList) {
            String messageCode = "AlertNotificationType.${alertNotification.type}"

            parsedAlertNotificationList.add([
                    publicId: alertNotification.publicId,
                    title: MessageUtils.getMessage("${messageCode}.title", null),
                    content: MessageUtils.getMessage("${messageCode}.content", [alertNotification.payment.payer.name,
                                                                                            CurrencyUtils.format(alertNotification.payment.value)]),
                    date: DateUtils.formatWithTime(alertNotification.createdAt)])
        }

        render(parsedAlertNotificationList as JSON, status: HttpStatus.OK.code)
    }
}
