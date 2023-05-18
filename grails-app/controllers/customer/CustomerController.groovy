package customer

class CustomerController {

    CustomerService customerService

    def index() {
        return [customers: customerService.findAll()]
    }

    def create() {
        return [:]
    }
}
