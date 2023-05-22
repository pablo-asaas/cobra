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
        return Customer.query([:]).list()
    }

    @ReadOnly
    public Customer findById(Long id) {
        Customer customer = Customer.query([id: id]).get()

        if (!customer) throw new RuntimeException("Cliente não encontrado")

        return customer
    }

    public void delete(Long id) {
        Customer customer = findById(id);
        customer.deleted = true
        customer.save(failOnError: true)
    }

    public void update(Long id, Map params) {
        Customer customer = findById(id)

        if (params.name) customer.name = params.name
        if (params.email) customer.email = params.email
        if (params.cpfCnpj) customer.cpfCnpj = params.cpfCnpj

        customer.save(failOnError: true)
    }
}
