package cobra.payer

import cobra.base.BaseController
import cobra.payer.adapter.PayerAdapter
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PayerController extends BaseController {

    PayerService payerService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE', restore: 'POST']

    def index() {
        if (params.deleted) {
            return [payerList: payerService.findAllDeleted(getCurrentCustomer())]
        }

        return [payerList: payerService.findAll(getCurrentCustomer())]
    }

    def save() {
        payerService.save(getCurrentCustomer(), new PayerAdapter(params))
        render([message: "Pagador criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def delete() {
        payerService.delete(getCurrentCustomer(), params.id as Long)
        render([message: "Pagador deletado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def show() {
        return [payer: payerService.findById(getCurrentCustomer(), params.id as Long)]
    }

    def update() {
        payerService.update(getCurrentCustomer(), params.id as Long, params)
        render([message: "Pagador editado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def restore() {
        payerService.restore(getCurrentCustomer(), params.id as Long)
        render([message: "Pagador restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }
}
