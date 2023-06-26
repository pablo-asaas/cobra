package cobra.customer

import cobra.customer.adapter.CustomerAdapter
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.util.MessageUtils
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.Transactional
import org.apache.commons.validator.routines.EmailValidator

@Transactional
class CustomerService {


    public Customer save(CustomerAdapter customerAdapter) {

        validateParams(customerAdapter)

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

        if (!customer) throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Cliente']))

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
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Nome']))
        }
        if (!customerAdapter.email){
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Endereço de email']))
        }
        if (!(new EmailValidator(false).isValid(customerAdapter.email as String))) {
            throw new BusinessException(MessageUtils.getMessage('default.invalid.message', ['Endereço de email']))
        }
        if (!customerAdapter.cpfCnpj) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['CPF/CNPJ']))
        }
        if (!customerAdapter.postalCode) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['CEP']))
        }
        if (!customerAdapter.streetName){
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Nome da rua']))
        }
        if (!customerAdapter.buildingNumber) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Número da residência']))
        }
        if (!customerAdapter.neighborhood) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Bairro']))
        }
        if (!customerAdapter.city) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Cidade']))
        }
        if (!customerAdapter.state) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Estado']))
        }
    }

    private void validateCpfCnpj(String cpfCnpj) {
        if (!cpfCnpj) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['CPF/CNPJ']))
        }
        if (!CpfCnpjValidator.validate(cpfCnpj)) {
            throw new BusinessException(MessageUtils.getMessage('default.invalid.message', ['CPF/CNPJ']))
        }
        if (Customer.countByCpfCnpj(cpfCnpj) > 0) {
            throw new BusinessException(MessageUtils.getMessage('default.alreadyExists.message', ['CPF/CNPJ']))
        }
    }
}
