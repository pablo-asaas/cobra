package cobra.authentication

import cobra.customer.Customer
import cobra.customer.CustomerService
import cobra.user.Role
import cobra.user.User
import cobra.user.UserRole
import grails.gorm.transactions.Transactional

@Transactional
class RegisterService {

    CustomerService customerService

    def save(Map params) {

        Customer customer = customerService.save(params)
        Role role = Role.get(1)

        User user = new User()
        if (params.username) user.username = params.username
        if (params.password) user.password = params.password
        if (customer) user.customer = customer
        user.save(failOnError: true)

        UserRole.create user, role
        UserRole.withSession {
            it.flush()
            it.clear()
        }
    }
}
