package customer

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class CustomerService {

    public void save(Map params) {
        Customer customer = new Customer()

        if (params.name) customer.name = params.name
        if (params.email) customer.email = params.email
        if (params.cpfCnpj) customer.cpfCnpj = params.cpfCnpj

        customer.save(failOnError: true)
    }

    @ReadOnly
    public List<Customer> findAll() {
        return Customer.where {}.findAll()
    }
}
