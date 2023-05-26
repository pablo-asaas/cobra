package cobra.authentication

import grails.plugin.springsecurity.SpringSecurityService
import org.springframework.security.access.annotation.Secured

@Secured('permitAll')
class RegisterController {

    SpringSecurityService springSecurityService
    RegisterService registerService

    static allowedMethods = [save: 'POST']

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
        }
        else {
            redirect action: 'create'
        }
    }

    def create() {

        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
            return
        }

        return [:]
    }

    def save(){
        registerService.save(params)
        redirect view: 'index'
    }
}
