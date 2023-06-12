package cobra.payer

import cobra.base.BaseController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PayerController extends BaseController {

    def payerService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE', restore: 'POST']

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

    def delete() {
        payerService.delete(getCurrentCustomer(), params.id as Long)
        render([message: "Deletado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def show() {
        return [payer: payerService.findById(getCurrentCustomer(), params.id as Long)]
    }

    def update() {
        payerService.update(getCurrentCustomer(), params.id as Long, params)
        render([message: "Editado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def restore() {
        payerService.restore(getCurrentCustomer(), params.id as Long)
        render([message: "Restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }
}
