package cobra.customer

import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.Payer
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class CustomerService {


    public Customer save(Map params) {

        validateParams(params)

        Customer customer = new Customer()
        customer.name = params.name
        customer.email = params.email
        customer.cpfCnpj = params.cpfCnpj

        return customer.save(failOnError: true)
    }

    @ReadOnly
    public List<Customer> findAll() {
        return Customer.query([:]).list()
    }

    public Customer findById(Long id) {
        Customer customer = Customer.query([id: id]).get()

        if (!customer) throw new ResourceNotFoundException("Cliente não encontrado")

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

    private void validateParams(Map params) {
        if (!params.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if(!params.email){
            throw new BusinessException("Email é obrigatório")
        }
        validateCpfCnpj(params.cpfCnpj)
    }

    private void validateCpfCnpj(String cpfCnpj) {
        if (!cpfCnpj) {
            throw new BusinessException("CPF/CNPJ é obrigatório")
        }
        if (!CpfCnpjValidator.validate(cpfCnpj)) {
            throw new BusinessException("CPF/CNPJ inválido")
        }
        if (Customer.countByCpfCnpj(cpfCnpj) > 0) {
            throw new BusinessException("CPF/CNPJ já cadastrado")
        }
    }
}
