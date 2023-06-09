package cobra.customer

import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.Payer
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import org.apache.commons.validator.routines.EmailValidator

@Transactional
class CustomerService {


    public Customer save(Map params) {

        validateParams(params)

        Customer customer = new Customer()
        customer.name = params.name
        customer.email = params.email
        customer.cpfCnpj = params.cpfCnpj

        customer.postalCode = params.postalCode
        customer.streetName = params.streetName
        customer.buildingNumber = params.buildingNumber
        if (params.complement) customer.complement = params.complement
        customer.neighborhood = params.neighborhood
        customer.city = params.city
        customer.state = params.state

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
        validateParams(params)

        Customer customer = findById(id)

        if (params.name) customer.name = params.name
        if (params.email) customer.email = params.email
        if (params.cpfCnpj) customer.cpfCnpj = params.cpfCnpj

        customer.postalCode = params.postalCode
        customer.streetName = params.streetName
        customer.buildingNumber = params.buildingNumber
        if (params.complement) customer.complement = params.complement
        customer.neighborhood = params.neighborhood
        customer.city = params.city
        customer.state = params.state

        customer.save(failOnError: true)
    }

    private void validateParams(Map params) {
        if (!params.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if (!params.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!(new EmailValidator(false).isValid(params.email as String))) {
            throw new BusinessException("Email inválido")
        }
        if (!params.cpfCnpj) {
            throw new BusinessException("Cpf/Cnpj é obrigatório")
        }
        if (!params.postalCode) {
            throw new BusinessException("CEP é obrigatório")
        }
        if (!params.streetName){
            throw new BusinessException("Nome da Rua é obrigatório")
        }
        if (!params.buildingNumber) {
            throw new BusinessException("Número é obrigatório")
        }
        if (!params.neighborhood) {
            throw new BusinessException("Bairro é obrigatório")
        }
        if (!params.city) {
            throw new BusinessException("Cidade é obrigatório")
        }
        if (!params.state) {
            throw new BusinessException("Estado é obrigatório")
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
