package cobra.payment.adapter

class UpdatePaymentAdapter extends BasePaymentAdapter{

    BigDecimal value

    UpdatePaymentAdapter(Map params) {
        super(params.dueDate)
        this.value = new BigDecimal(params.value)
    }
}
