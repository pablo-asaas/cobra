package cobra.domain

abstract class BaseDomain {

    Date createdAt = new Date()
    boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }
}
