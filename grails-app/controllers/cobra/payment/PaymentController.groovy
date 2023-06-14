package cobra.payment

import cobra.base.BaseController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PaymentController extends BaseController {

    PaymentService paymentService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT', delete: 'DELETE']

    def index() {
        if (params.deleted) {
            return [paymentList: paymentService.findAllDeleted(getCurrentCustomer())]
        }

        return [paymentList: paymentService.findAll(getCurrentCustomer())]
    }

    def show() {
        return [payment: paymentService.findByPublicId(getCurrentCustomer(), params.id as String)]
    }

    def save() {
        paymentService.save(getCurrentCustomer(), params)
        render([message: "Cobrança criada com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def delete(Long id) {
        paymentService.delete(getCurrentCustomer(), id)
        render([message: "Cobrança excluída com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def update() {
        paymentService.update(getCurrentCustomer(), params.id as Long, params)
        render([message: "Cobrança editada com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def restore() {
        paymentService.restore(getCurrentCustomer(), params.id as Long, params)
        render([message: "Pagamento restaurado com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def confirmPayment() {
        if (params.deposit) {
            paymentService.confirmDeposit(getCurrentCustomer(), params.id as Long)
            render([message: "Pagamento confirmado com sucesso"] as JSON, status: HttpStatus.OK.code)
        }
    }
}
