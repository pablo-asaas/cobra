package cobra.payment

import cobra.base.BaseDomain
import cobra.customer.Customer
import cobra.payer.Payer

class Payment extends BaseDomain {

    Customer owner
    Payer payer
    BillingType billingType
    BigDecimal value
    BillingStatus status
    Date expiresAt

    static constraints = {
        value min: BigDecimal.ZERO
    }

    static namedQueries = {
        query { Map search ->
            if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("id")) {
                eq("id", search.id)
            }
        }
    }
}
