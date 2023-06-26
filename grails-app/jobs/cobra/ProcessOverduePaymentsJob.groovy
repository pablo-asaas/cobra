package cobra

import cobra.payment.PaymentService

class ProcessOverduePaymentsJob {

    PaymentService paymentService

    static triggers = {
        cron name: "ProcessOverduePaymentsJobTrigger", startDelay: 10000, cronExpression: "0 0 0 * * ?"
    }

    static concurrent = false

    void execute() {
        paymentService.processToOverdue()
    }
}
