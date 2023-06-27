package cobra.payment

import cobra.base.BaseDomain
import cobra.customer.Customer
import cobra.payer.Payer

class Payment extends BaseDomain {

    public static final BigDecimal PAYMENT_MINIMUM_VALUE = new BigDecimal("0.01")

    String publicId = UUID.randomUUID().toString()
    Customer customer
    Payer payer
    PaymentType type
    BigDecimal value
    PaymentStatus status = PaymentStatus.PENDING
    Date dueDate
    Date paymentDate

    static mapping = {
        publicId unique: true, length: 36
    }

    static constraints = {
        value validator: { val, obj, errors ->
            if (val < PAYMENT_MINIMUM_VALUE) {
                errors.rejectValue("value", null)
            }
        }
        dueDate validator: { val, obj, errors ->
            if (val <= obj.createdAt) {
                errors.rejectValue("dueDate", null)
            }
        }
        paymentDate nullable: true
    }

    static namedQueries = {
        query { Map search ->
            if (!Boolean.valueOf(search.ignoreCustomer) && !search.customer) {
                throw new RuntimeException("É obrigatório informar um cliente para executar a consulta")
            }

            if (search.containsKey("customer")) {
                eq("customer", search.customer)
            }

            if (Boolean.valueOf(search.onlyDeleted)) {
                eq("deleted", true)
            } else if (!Boolean.valueOf(search.includeDeleted)) {
                eq("deleted", false)
            }

            if (search.containsKey("id")) {
                eq("id", search.id)
            }

            if (search.containsKey("dueDate[lt]")) {
                lt("dueDate", search."dueDate[lt]")
            }

            if (search.containsKey("status")) {
                eq("status", search.status)
            }

            if (search.containsKey("publicId")) {
                eq("publicId", search.publicId)
            }

            if (search.containsKey("fromDate") && search.containsKey("toDate")) {
                between("paymentDate", search.fromDate, search.toDate)
            }

            if (search.containsKey("column")) {
                if (search.sum) {
                    projections {
                        sum(search.column)
                    }
                } else {
                    projections {
                        property(search.column)
                    }
                }
            }

            if (search.containsKey("offset")) {
                firstResult(search.offset)
            }

            if (search.containsKey("limit")) {
                maxResults(search.limit)
            }
        }
        countByPaymentType { Map search ->
            query(search)

            projections {
                groupProperty ("type")
                count ("type")
            }
        }
    }
}
