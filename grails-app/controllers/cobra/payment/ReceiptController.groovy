package cobra.payment

import cobra.exception.ResourceNotFoundException
import grails.converters.JSON
import io.micronaut.http.HttpStatus

class ReceiptController {

    PaymentService paymentService

    def show() {
        try {
            return [payment: paymentService.getPaymentReceipt(params.id)]
        } catch (ResourceNotFoundException exception) {
            render([message: exception.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }  catch (Exception exception) {
            exception.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
        }
    }
}
