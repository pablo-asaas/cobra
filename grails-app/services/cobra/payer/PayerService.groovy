package cobra.payer

import cobra.customer.Customer
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    @ReadOnly
    public List<Payer> findAll(Customer customer){
        return Payer.query([customer: customer]).list()
    }

    @ReadOnly
    public Payer findById(Customer customer, Long id){
        Payer payer = Payer.query([id: id, customer: customer]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        return payer
    }

    public void save(Customer customer, Map params){
        Payer payer = new Payer()
        bind(customer, payer, params)
        payer.save(failOnError: true)
    }

    public void delete(Customer customer, Long id){
        Payer payer = findById(customer, id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Customer customer, Long id, Map params){
        Payer payer = findById(customer, id)
        bind(customer, payer, params)
        payer.save(failOnError: true)
    }

    private void bind(Customer customer, Payer payer, Map params){
        if (params.name) payer.name = params.name
        if (params.email) payer.email = params.email
        if (params.cpfCnpj) payer.cpfCnpj = params.cpfCnpj
        if (params.phoneNumber) payer.phoneNumber = params.phoneNumber
        if (customer) payer.customer = customer
    }
}
