package cobra.user

import cobra.customer.Customer
import cobra.customer.CustomerService
import cobra.customer.adapter.CustomerAdapter
import cobra.exception.BusinessException
import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    CustomerService customerService

    public void save(Map params) {
        Customer customer = customerService.save(new CustomerAdapter(params))
        save(customer, params)
    }

    public void save(Customer customer, Map params) {
        validateParams(params)

        Role role = Role.find {authority == "ROLE_USER"}

        User user = new User()
        user.username = params.username
        user.password = params.password
        user.customer = customer
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

    private void validateParams(Map params) {
        if (!params.username) {
            throw new BusinessException("Nome de usuário inválido")
        }
        if (!params.password) {
            throw new BusinessException("Senha inválida")
        }
        if (User.query([exists: true, username: params.username]).get().asBoolean()) {
            throw new BusinessException("Nome de usuário já cadastrado")
        }
    }
}
