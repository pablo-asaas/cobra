package cobra.base

import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class BaseDomain {

    Date createdAt = new Date()
    boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }
}
