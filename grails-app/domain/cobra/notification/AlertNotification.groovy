package cobra.notification

import cobra.base.BaseDomain
import cobra.customer.Customer
import cobra.payment.Payment

class AlertNotification extends BaseDomain {

    String publicId = UUID.randomUUID().toString()
    Customer customer
    Payment payment
    AlertNotificationType type
    Boolean unread = true

    static namedQueries = {
        query { Map search ->
            if (!Boolean.valueOf(search.ignoreCustomer) && !search.customer) {
                throw new RuntimeException("É obrigatório informar um cliente para executar a consulta")
            }

            if (search.containsKey("customer")) {
                eq("customer", search.customer)
            }

            if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (!Boolean.valueOf(search.includeRead)) {
                eq("unread", true)
            }

            if (search.containsKey("limit")) {
                maxResults(search.limit)
            }

            if (search.containsKey("createdAt[order]")) {
                order("createdAt", search."createdAt[order]")
            }

            if (search.containsKey("id")) {
                eq("id", search.id)
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }
        }
    }
}
