package cobra.payer

import cobra.base.BasePerson

class Payer extends BasePerson{

    String phoneNumber

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
        }
    }
}
