package customer

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
        } catch (Exception exception) {
            exception.printStackTrace()
            redirect action: 'create'
        }
    }
}
