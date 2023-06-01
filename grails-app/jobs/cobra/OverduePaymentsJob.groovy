package cobra

import cobra.payment.PaymentService

class OverduePaymentsJob {

    PaymentService paymentService

    static triggers = {
        cron cronExpression: "0 0 0 * * ?"
    }

    void execute() {
        paymentService.processToOverdue()
    }
}
