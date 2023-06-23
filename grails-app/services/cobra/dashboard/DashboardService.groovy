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

    public Map doughnutGraphInfo (Customer customer) {
        def countTypeList = Payment.countByPaymentType([customer: customer]).list()
        def countTypeMap = countTypeList.collectEntries {
            [it[0].toString().toLowerCase(), it[1]]
        }
        return countTypeMap
    }

    public Map barGraphInfo(Customer customer) {

        return [current: 1234, last: 321, lastButOne: 456]
    }

    private BigDecimal calculateMonthlyBilling(Customer customer) {
        def fromDate = DateUtils.getStartOfMonth()
        def toDate = new Date()
        return Payment.query(customer: customer, status: PaymentStatus.PAID, column: "value", sum: true, fromDate: fromDate, toDate: toDate).get()
    }

    private Long calculatePaymentsAmountByStatus(Customer customer, PaymentStatus status) {
        return Payment.query(customer: customer, status: status).count()
    }

    private BigDecimal calculateTotalReceivable(Customer customer) {
        return Payment.query(customer: customer, status: PaymentStatus.PENDING, column: "value", sum: true).get()
    }
}
