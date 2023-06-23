package cobra

import cobra.payment.PaymentService

class ProcessOverduePaymentsJob {

    PaymentService paymentService

    static concurrent = false

    static triggers = {
        cron name: "ProcessOverduePaymentsJobTrigger", startDelay: 10000, cronExpression: "0 0 0 * * ?"
    }

    void execute() {
        paymentService.processToOverdue()
    }
}
