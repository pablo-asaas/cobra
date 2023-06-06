package cobra

import cobra.user.Role
import grails.gorm.transactions.Transactional

class BootStrap {

    def init = {
        verifyRoleUser()
    }
    @Transactional
    void verifyRoleUser() {
        Role role = Role.find{authority == "ROLE_USER"}

        if(!role) {
            new Role(authority: "ROLE_USER").save(failOnError: true)
        }
    }
    def destroy = {
    }
}
