package cobra.user

import cobra.customer.Customer
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class UserController {

    UserService userService
    SpringSecurityService springSecurityService

    def index() {
        return [userList: userService.findAll(getCurrentCustomer()), currentUser: springSecurityService.getCurrentUser().username]
    }

    def create() {
        return [:]
    }

    def save() {
        println params
        println getCurrentCustomer()
        userService.save(getCurrentCustomer(), Map params)
        redirect(view: 'create')
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
