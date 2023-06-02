package cobra.payer

import cobra.base.BasePerson
import cobra.customer.Customer

class Payer extends BasePerson{

    String phoneNumber
    Customer customer

    static constraints = {
        phoneNumber blank: false, size: 13..13
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
            } else {
                eq("customer", search.customer)
            }
        }
    }
}
