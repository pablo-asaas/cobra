package cobra.dashboard

import cobra.base.BaseController
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class DashboardController extends BaseController{

    def index() {
        return [dashboard: "dashboard"]
    }
}
