package cobra.payer

import cobra.base.BaseController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PayerController extends BaseController {

    def payerService

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
        payerService.save(getCurrentCustomer(), params)
        render([message: "Criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def delete(Long id) {
        payerService.delete(getCurrentCustomer(), id)
        render([message: "Deletado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def show (Long id) {
        return [payer: payerService.findById(getCurrentCustomer(), id)]
    }

    def update() {
        payerService.update(getCurrentCustomer(), params.id as Long, params)
        render([message: "Editado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def restore(Long id) {
        payerService.restore(getCurrentCustomer(), id)
        render([message: "Restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }
}
