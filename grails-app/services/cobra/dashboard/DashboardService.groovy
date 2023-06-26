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
        BigDecimal monthlyBilling = calculateMonthlyBilling(customer, DateUtils.getStartOfMonth(), new Date())
        Long pendingPaymentsAmount = calculatePaymentsAmountByStatus(customer, PaymentStatus.PENDING)
        Long overduePaymentsAmount = calculatePaymentsAmountByStatus(customer, PaymentStatus.OVERDUE)
        BigDecimal totalReceivable = calculateTotalReceivable(customer)

        return [monthlyBilling: monthlyBilling,
                pendingPaymentsAmount: pendingPaymentsAmount,
                overduePaymentsAmount: overduePaymentsAmount,
                totalReceivable: totalReceivable]
    }

    public Map mostUsedPaymentType(Customer customer) {
        def countTypeList = Payment.countByPaymentType([customer: customer]).list()
        def countTypeMap = countTypeList.collectEntries {
            [it[0].toString().toLowerCase(), it[1]]
        }
        return countTypeMap
    }

    public Map lastThreeMonthsBilling(Customer customer) {

        BigDecimal currentMonthBilling = calculateMonthlyBilling(customer, DateUtils.getStartOfMonth(), new Date())

        Date lastMonth = DateUtils.subtractOrAddMonths(-1)
        Date lastMonthStart = DateUtils.getStartOfMonth(lastMonth)
        Date lastMonthEnd = DateUtils.getEndOfMonth(lastMonth)
        BigDecimal lastMonthBilling = calculateMonthlyBilling(customer, lastMonthStart, lastMonthEnd)

        Date lastButOneMonth = DateUtils.subtractOrAddMonths(-2)
        Date lastButOneMonthStart = DateUtils.getStartOfMonth(lastButOneMonth)
        Date lastButOneMonthEnd = DateUtils.getEndOfMonth(lastButOneMonth)
        BigDecimal lastButOneMonthBilling = calculateMonthlyBilling(customer, lastButOneMonthStart, lastButOneMonthEnd)

        return [current: currentMonthBilling, last: lastMonthBilling, lastButOne: lastButOneMonthBilling]
    }

    private BigDecimal calculateMonthlyBilling(Customer customer, Date fromDate, Date toDate) {
        return Payment.query(customer: customer, status: PaymentStatus.PAID, column: "value", sum: true, fromDate: fromDate, toDate: toDate).get()
    }

    private Long calculatePaymentsAmountByStatus(Customer customer, PaymentStatus status) {
        return Payment.query(customer: customer, status: status).count()
    }

    private BigDecimal calculateTotalReceivable(Customer customer) {
        return Payment.query(customer: customer, status: PaymentStatus.PENDING, column: "value", sum: true).get()
    }
}
