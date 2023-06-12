package cobra.payer

import cobra.customer.Customer
import cobra.user.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import grails.converters.JSON
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PayerController {

    def payerService
    SpringSecurityService springSecurityService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', restore: 'POST']

    def create() {
        return [:]
    }

    def index() {
        if (params.deleted) {
            return [payerList: payerService.findAllDeleted(getCurrentCustomer())]
        }
        return [payerList: payerService.findAll(getCurrentCustomer())]
    }

    def save() {
        try {
            payerService.save(getCurrentCustomer(), params)
            render([message: "Criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    def delete(Long id) {
        try {
            payerService.delete(getCurrentCustomer(), id)
            render([message: "Deletado com sucesso"] as JSON, status: HttpStatus.OK.code)
        }catch(ResourceNotFoundException e){
            render(view: "/notFound", model: [message: e.getMessage()], status: HttpStatus.NOT_FOUND.code)
        }catch (Exception e) {
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    def show (Long id) {
        try {
            return [payer: payerService.findById(getCurrentCustomer(), id)]
        }catch (ResourceNotFoundException e){
            render(view: "/notFound", model: [message: e.getMessage()], status: HttpStatus.NOT_FOUND.code)
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }

    def update() {
        try {
            payerService.update(getCurrentCustomer(), params.id as Long, params)
            render([message: "Editado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (ResourceNotFoundException e){
            render(view: "/notFound", model: [message: e.getMessage()], status: HttpStatus.NOT_FOUND.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    def restore(Long id) {
        try{
            payerService.restore(getCurrentCustomer(), id)
            render([message: "Restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
        }catch (ResourceNotFoundException e){
            render(view: "/notFound", model: [message: e.getMessage()], status: HttpStatus.NOT_FOUND.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
