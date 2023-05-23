package cobra.customer

import cobra.base.BasePerson

class Customer extends BasePerson {

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