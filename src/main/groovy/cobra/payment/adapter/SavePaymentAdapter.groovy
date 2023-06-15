package cobra.payment.adapter

import cobra.payment.PaymentType

class SavePaymentAdapter extends BasePaymentAdapter{

    Long payerId
    PaymentType type
    BigDecimal value

    SavePaymentAdapter(Date dueDate, Long payerId, PaymentType type, BigDecimal value) {
        super(dueDate)
        this.payerId = payerId
        this.type = type
        this.value = value
    }
}
