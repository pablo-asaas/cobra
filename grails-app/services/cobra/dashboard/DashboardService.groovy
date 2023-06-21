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
        Long pendingPaymentsAmount = calculatePendingPaymentsAmount(customer)
        Long overduePaymentsAmount = calculateOverduePaymentsAmount(customer)

        return [monthlyBilling: monthlyBilling,
                pendingPaymentsAmount: pendingPaymentsAmount,
                overduePaymentsAmount: overduePaymentsAmount,
                totalReceivable: 1234]
    }

    private BigDecimal calculateMonthlyBilling(Customer customer) {
        def fromDate = DateUtils.getStartOfMonth()
        def toDate = new Date()
        return Payment.query(customer: customer, status: PaymentStatus.PAID, column: "value", fromDate: fromDate, toDate: toDate).get()
    }

    private Long calculatePendingPaymentsAmount(Customer customer) {
        return Payment.query(customer: customer, status: PaymentStatus.PENDING).count()
    }

    private Long calculateOverduePaymentsAmount(Customer customer) {
        return Payment.query(customer: customer, status: PaymentStatus.OVERDUE).count()
    }
}
