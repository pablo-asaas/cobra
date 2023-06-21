package cobra.dashboard

import cobra.base.BaseController
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class DashboardController extends BaseController{

    def index() {
        return [monthlyBilling: 100, pendingPaymentsAmount: 6, overduePaymentsAmount: 9, totalReceivable: 1234]
    }
}
