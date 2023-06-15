package cobra.payment.adapter

import java.text.SimpleDateFormat

abstract class BasePaymentAdapter {

    Date dueDate

    BasePaymentAdapter(Date dueDate) {
        this.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDate)
    }
}
