package cobra.payment

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.user.User
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PaymentController {

    PaymentService paymentService
    SpringSecurityService springSecurityService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    def index() {
        return [paymentList: paymentService.findAll(getCurrentCustomer())]
    }

    def show(Long id) {
        try {
            return [payment: paymentService.findById(getCurrentCustomer(), id)]
        } catch (ResourceNotFoundException exception) {
            render(view: "/notFound", model: [message: exception.message], status: HttpStatus.NOT_FOUND.code)
        } catch (Exception exception) {
            exception.printStackTrace()
            redirect action: "index"
        }
    }

    def save() {
        try {
            paymentService.save(getCurrentCustomer(), params)
            render([message: "Cobrança criada com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        } catch (BusinessException exception) {
            render([message: exception.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        } catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
        }
    }

    def delete(Long id) {
        try {
            paymentService.delete(getCurrentCustomer(), id)
            render([message: "Cobrança excluída com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        } catch (ResourceNotFoundException exception) {
            render(view: "/notFound", model: [message: exception.message], status: HttpStatus.NOT_FOUND.code)
        } catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
        }
    }

    def update() {
        try {
            paymentService.update(getCurrentCustomer(), params.id as Long, params)
            render([message: "Cobrança editada com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        } catch (ResourceNotFoundException exception) {
            render(view: "/notFound", model: [message: exception.message], status: HttpStatus.NOT_FOUND.code)
        } catch (BusinessException exception) {
            render([message: exception.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        } catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
        }
    }

    def restore(Long id) {
        try {
            paymentService.restore(getCurrentCustomer(), id, params)
            render([message: "Pagamento restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
        } catch (BusinessException exception) {
            render([message: exception.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        } catch (ResourceNotFoundException exception) {
            render(view: "/notFound", model: [message: exception.message], status: HttpStatus.NOT_FOUND.code)
        } catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
        }
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
