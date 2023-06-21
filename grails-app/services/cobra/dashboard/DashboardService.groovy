package cobra.dashboard

import cobra.customer.Customer
import cobra.payment.Payment
import cobra.payment.PaymentStatus
import grails.gorm.transactions.Transactional

import java.time.LocalDate

@Transactional
class DashboardService {

    public Map dashboardInfo(Customer customer) {
        BigDecimal monthlyBilling = calculateMonthlyBilling(customer)
        return [monthlyBilling: monthlyBilling,
                pendingPaymentsAmount: 6,
                overduePaymentsAmount: 9,
                totalReceivable: 1234]
    }

    private BigDecimal calculateMonthlyBilling(Customer customer) {
        def fromDate = getStartOfMonth()
        def toDate = new Date()
        return Payment.query(customer: customer, status: PaymentStatus.PAID, column: "value", fromDate: fromDate, toDate: toDate).get()
    }
    private static Date getStartOfMonth() {
        Calendar calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        return calendar.getTime()
    }
}
