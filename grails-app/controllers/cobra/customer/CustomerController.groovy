package cobra.customer

import cobra.base.BaseController
import cobra.customer.adapter.CustomerAdapter
import grails.plugin.springsecurity.annotation.Secured
import grails.converters.JSON
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class CustomerController extends BaseController{

    CustomerService customerService

    def show () {
        return [customer: customerService.findById(getCurrentCustomer().id)]
    }

    def update() {
        customerService.update(params.id as Long, new CustomerAdapter(params))
        render([message: "Editado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }
}
