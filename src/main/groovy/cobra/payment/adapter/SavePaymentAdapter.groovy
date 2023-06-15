package cobra.payment.adapter

import cobra.payment.PaymentType

class SavePaymentAdapter extends BasePaymentAdapter{

    Long payerId
    PaymentType type
    BigDecimal value

    SavePaymentAdapter(Map params) {
        super(params.dueDate)
        this.payerId = params.payerId
        this.type = params.type
        this.value = params.value
    }
}
