package cobra.payment.adapter

class RestorePaymentAdapter extends BasePaymentAdapter{

    RestorePaymentAdapter(Map params) {
        super(params.dueDate)
    }
}
