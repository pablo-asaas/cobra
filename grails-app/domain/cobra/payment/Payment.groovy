package cobra.payment

import cobra.base.BaseDomain
import cobra.customer.Customer
import cobra.payer.Payer

class Payment extends BaseDomain {

    Customer customer
    Payer payer
    PaymentType type
    BigDecimal value
    PaymentStatus status = PaymentStatus.PENDING
    Date dueDate

    static constraints = {
        value validator: { val, obj, errors ->
            if (val <= BigDecimal.ZERO) {
                errors.rejectValue("value", null)
            }
        }
        dueDate validator: { val, obj, errors ->
            if (val <= obj.createdAt) {
                errors.rejectValue("dueDate", null)
            }
        }
    }

    static namedQueries = {
        query { Map search ->
            if (!search.containsKey("customer")) {
                throw new RuntimeException("É obrigatório informar um cliente para executar a consulta")
            } else {
                eq("customer", search.customer)
            }

            if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("id")) {
                eq("id", search.id)
            }
        }
    }
}
