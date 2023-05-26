package cobra.authentication

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured('permitAll')
class LoginController {

    SpringSecurityService springSecurityService

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
        }
        else {
            redirect action: 'auth'
        }
    }

    def auth() {

        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
            return
        }

        render view: 'auth'
    }
}
