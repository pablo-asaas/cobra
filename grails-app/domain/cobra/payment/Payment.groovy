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

//            if (val < new Date()) {
//                errors.rejectValue("dueDate", null)
//                return false
//            }
        }
    }

    static namedQueries = {
        query { Map search ->
            if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("id")) {
                eq("id", search.id)
            }

            if (!search.containsKey("customer")) {
                throw new RuntimeException("É obrigatório informar um cliente para executar a consulta")
            }

            eq("customer", search.customer)
        }
    }
}
