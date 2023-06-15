package cobra.payment.adapter

class UpdatePaymentAdapter extends BasePaymentAdapter{

    BigDecimal value

    UpdatePaymentAdapter(Date dueDate, BigDecimal value) {
        super(dueDate)
        this.value = value
    }
}
