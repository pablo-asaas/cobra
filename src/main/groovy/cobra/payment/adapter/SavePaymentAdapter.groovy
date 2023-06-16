package cobra.payment.adapter

import cobra.payment.PaymentType

class SavePaymentAdapter extends BasePaymentAdapter {

    Long payerId
    PaymentType type
    BigDecimal value

    SavePaymentAdapter(Map params) {
        super(params.dueDate)
        this.payerId = params.payer as Long
        this.type = PaymentType.valueOf(params.type.toUpperCase())
        this.value = new BigDecimal(params.value)
    }
}
