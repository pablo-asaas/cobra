package cobra.payment.adapter

class RestorePaymentAdapter extends BasePaymentAdapter {

    public RestorePaymentAdapter(Map params) {
        super(params.dueDate)
    }
}
