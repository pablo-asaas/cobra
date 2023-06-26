package cobra.customer

import cobra.customer.adapter.CustomerAdapter
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.Transactional
import org.apache.commons.validator.routines.EmailValidator

@Transactional
class CustomerService {


    public Customer save(CustomerAdapter customerAdapter) {

        validateParams(customerAdapter)
        validateCpfCnpj(customerAdapter.cpfCnpj)

        Customer customer = new Customer()
        customer.name = customerAdapter.name
        customer.email = customerAdapter.email
        customer.cpfCnpj = customerAdapter.cpfCnpj

        customer.postalCode = customerAdapter.postalCode
        customer.streetName = customerAdapter.streetName
        customer.buildingNumber = customerAdapter.buildingNumber
        customer.complement = customerAdapter.complement
        customer.neighborhood = customerAdapter.neighborhood
        customer.city = customerAdapter.city
        customer.state = customerAdapter.state

        return customer.save(failOnError: true)
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

    public void update(Long id, CustomerAdapter customerAdapter) {
        validateParams(customerAdapter)

        Customer customer = findById(id)

        customer.name = customerAdapter.name
        customer.email = customerAdapter.email
        if (customer.cpfCnpj != customerAdapter.cpfCnpj) {
            validateCpfCnpj(customerAdapter.cpfCnpj)
            customer.cpfCnpj = customerAdapter.cpfCnpj
        }

        customer.postalCode = customerAdapter.postalCode
        customer.streetName = customerAdapter.streetName
        customer.buildingNumber = customerAdapter.buildingNumber
        customer.complement = customerAdapter.complement
        customer.neighborhood = customerAdapter.neighborhood
        customer.city = customerAdapter.city
        customer.state = customerAdapter.state

        customer.save(failOnError: true)
    }

    private void validateParams(CustomerAdapter customerAdapter) {
        if (!customerAdapter.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if (!customerAdapter.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!(new EmailValidator(false).isValid(customerAdapter.email as String))) {
            throw new BusinessException("Email inválido")
        }
        if (!customerAdapter.postalCode) {
            throw new BusinessException("CEP é obrigatório")
        }
        if (!customerAdapter.streetName){
            throw new BusinessException("Nome da Rua é obrigatório")
        }
        if (!customerAdapter.buildingNumber) {
            throw new BusinessException("Número da residência é obrigatório")
        }
        if (!customerAdapter.neighborhood) {
            throw new BusinessException("Bairro é obrigatório")
        }
        if (!customerAdapter.city) {
            throw new BusinessException("Cidade é obrigatório")
        }
        if (!customerAdapter.state) {
            throw new BusinessException("Estado é obrigatório")
        }
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
