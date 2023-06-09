package cobra

import cobra.user.Role

class BootStrap {

    def init = {
        verifyRoleUser()
    }
    void verifyRoleUser() {
        Role role = Role.find{authority == "ROLE_USER"}

        if(!role) {
            new Role(authority: "ROLE_USER").save(failOnError: true)
        }
    }
    def destroy = {
    }
}
