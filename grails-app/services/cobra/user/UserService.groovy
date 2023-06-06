package cobra.user

import cobra.customer.Customer
import grails.gorm.transactions.Transactional

@Transactional
class UserService {


    public void save(Customer customer, Map params) {

        Role role = Role.find {authority == "ROLE_USER"}

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

    public List<User> findAll(Customer customer) {
        User.findAllByCustomer(customer)
    }
}
