package cobra.user

import cobra.customer.Customer
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class UserController {

    UserService userService
    SpringSecurityService springSecurityService

    def index() {
        redirect(view: 'create')
    }

    def create() {
        return [:]
    }

    def save() {
        println params
        println getCurrentCustomer()
        userService.save(getCurrentCustomer(), params)
        redirect(view: 'create')
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
