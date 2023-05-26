package cobra.payer

import cobra.customer.Customer
import cobra.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
class PayerController {

    def payerService
    SpringSecurityService springSecurityService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT']

    def create() {
        return [:]
    }

    def index() {
        return [payerList: payerService.findAll(getCurrentCustomer())]
    }

    def save() {
        try {
            payerService.save(getCurrentCustomer(), params)
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }

    def delete(Long id) {
        try {
            payerService.delete(getCurrentCustomer(), id)
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }

    def show (Long id) {
        return [payer: payerService.findById(getCurrentCustomer(), id)]
    }

    def update() {
        try {
            payerService.update(getCurrentCustomer(), params.id as Long, params)
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
