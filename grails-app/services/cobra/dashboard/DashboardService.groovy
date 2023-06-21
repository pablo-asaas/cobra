package cobra.dashboard

import grails.gorm.transactions.Transactional

@Transactional
class DashboardService {

    public Map dashboardInfo() {
        return [monthlyBilling: 100, pendingPaymentsAmount: 6, overduePaymentsAmount: 9, totalReceivable: 1234]
    }
}
