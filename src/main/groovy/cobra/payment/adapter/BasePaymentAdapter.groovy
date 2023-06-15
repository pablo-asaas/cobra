package cobra.payment.adapter

abstract class BasePaymentAdapter {

    Date dueDate

    BasePaymentAdapter(Date dueDate) {
        this.dueDate = dueDate
    }
}
