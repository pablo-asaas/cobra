package cobra.payment

import cobra.base.BaseController
import cobra.customer.Customer
import cobra.payment.adapter.RestorePaymentAdapter
import cobra.payment.adapter.SavePaymentAdapter
import cobra.payment.adapter.UpdatePaymentAdapter
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class PaymentController extends BaseController {

    PaymentService paymentService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PATCH', delete: 'DELETE', restore: 'POST']

    def index() {
        Customer currentCustomer = getCurrentCustomer()

        if (params.deleted) {
            return [paymentList: paymentService.findAllDeleted(currentCustomer,
                                                               params.offset as Integer,
                                                               params.max as Integer ?: DEFAULT_PAGINATION_LIMIT),
                    paymentCount: paymentService.countDeleted(currentCustomer)]
        }

        return [paymentList: paymentService.findAll(currentCustomer,
                                                    params.offset as Integer,
                                                    params.max as Integer ?: DEFAULT_PAGINATION_LIMIT),
                paymentCount: paymentService.count(currentCustomer)]
    }

    def show() {
        return [payment: paymentService.findById(getCurrentCustomer(), params.id as Long)]
    }

    def save() {
        paymentService.save(getCurrentCustomer(), new SavePaymentAdapter(params))
        render([message: "Cobrança criada com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }

    def delete() {
        paymentService.delete(getCurrentCustomer(), params.id as Long)
        render([message: "Cobrança excluída com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def update() {
        paymentService.update(getCurrentCustomer(), params.id as Long, new UpdatePaymentAdapter(params))
        render([message: "Cobrança editada com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def restore() {
        paymentService.restore(getCurrentCustomer(), params.id as Long, new RestorePaymentAdapter(params))
        render([message: "Cobrança restaurada com sucesso"] as JSON, status: HttpStatus.OK.code)
    }

    def confirmPayment() {
        if (params.deposit) {
            paymentService.confirmDeposit(getCurrentCustomer(), params.id as Long)
            render([message: "Pagamento confirmado com sucesso"] as JSON, status: HttpStatus.OK.code)
        }
    }
}
