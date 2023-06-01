package cobra.customer

import cobra.customer.CustomerService
import cobra.exception.BusinessException
import grails.converters.JSON
import grails.validation.ValidationException
import io.micronaut.http.HttpStatus

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
            render([message: "Criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        } catch (BusinessException exception) {
            exception.printStackTrace()
            render([message: exception.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }  catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }
}
