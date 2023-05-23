package cobra.customer

import cobra.customer.CustomerService
import grails.validation.ValidationException

class CustomerController {

    CustomerService customerService

    static allowedMethods = [save: 'POST']

    def index() {
        return [customers: customerService.findAll()]
    }

    def create() {
        return [:]
    }

    def save() {
        try {
            customerService.save(params)
            redirect action: 'index'
        } catch (ValidationException validationException) {
            validationException.printStackTrace()
            respond validationException.errors, view: 'create'
        } catch (Exception exception) {
            exception.printStackTrace()
            redirect action: 'create'
        }
    }
}
