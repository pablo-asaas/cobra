package cobra.dashboard

import cobra.customer.Customer
import cobra.payment.Payment
import cobra.payment.PaymentStatus
import cobra.util.DateUtils
import grails.gorm.transactions.Transactional

import java.time.LocalDate

@Transactional
class DashboardService {

    public Map dashboardInfo(Customer customer) {
        BigDecimal monthlyBilling = calculateMonthlyBilling(customer)
        Long pendingPaymentsAmount = calculatePaymentsAmountByStatus(customer, PaymentStatus.PENDING)
        Long overduePaymentsAmount = calculatePaymentsAmountByStatus(customer, PaymentStatus.OVERDUE)
        BigDecimal totalReceivable = calculateTotalReceivable(customer)

        return [monthlyBilling: monthlyBilling,
                pendingPaymentsAmount: pendingPaymentsAmount,
                overduePaymentsAmount: overduePaymentsAmount,
                totalReceivable: totalReceivable]
    }

    private BigDecimal calculateMonthlyBilling(Customer customer) {
        def fromDate = DateUtils.getStartOfMonth()
        def toDate = new Date()
        return Payment.query(customer: customer, status: PaymentStatus.PAID, column: "value", fromDate: fromDate, toDate: toDate).get()
    }

    private Long calculatePaymentsAmountByStatus(Customer customer, PaymentStatus status) {
        return Payment.query(customer: customer, status: status).count()
    }

    private BigDecimal calculateTotalReceivable(Customer customer) {
        return Payment.query(customer: customer, status: PaymentStatus.PENDING, column: "value").get()
    }
}
