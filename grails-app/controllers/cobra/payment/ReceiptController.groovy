package cobra.payment

import cobra.base.BaseController

class ReceiptController extends BaseController {

    PaymentService paymentService

    def show() {
        return [payment: paymentService.getPaymentReceipt(params.id)]
    }
}
