package cobra.dashboard

import cobra.base.BaseController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class DashboardController extends BaseController {

    DashboardService dashboardService

    def index() {
        return [cardInfo: dashboardService.dashboardInfo(getCurrentCustomer()),
                doughnutGraphInfo: dashboardService.doughnutGraphInfo(getCurrentCustomer()) as JSON]
    }
}
