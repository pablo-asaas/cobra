package cobra.payer

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.adapter.PayerAdapter
import cobra.util.MessageUtils
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import org.apache.commons.validator.routines.EmailValidator

@Transactional
class PayerService {

    @ReadOnly
    public List<Payer> findAll(Customer customer){
        return Payer.query([customer: customer]).list()
    }

    @ReadOnly
    public List<Payer> findAllDeleted(Customer customer){
        return Payer.query([customer: customer, onlyDeleted: true]).list()
    }

    public Payer findById(Customer customer, Long id){
        Payer payer = Payer.query([id: id, customer: customer]).get()

        if (!payer) throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Pagador']))

        return payer
    }


    public void save(Customer customer, PayerAdapter payerAdapter){
        validateParams(payerAdapter)
        validateCpfCnpj(customer, payerAdapter.cpfCnpj)

        Payer payer = new Payer()
        payer.name = payerAdapter.name
        payer.email = payerAdapter.email
        payer.cpfCnpj = payerAdapter.cpfCnpj
        payer.phoneNumber = payerAdapter.phoneNumber
        payer.customer = customer

        payer.postalCode = payerAdapter.postalCode
        payer.streetName = payerAdapter.streetName
        payer.buildingNumber = payerAdapter.buildingNumber
        payer.complement = payerAdapter.complement
        payer.neighborhood = payerAdapter.neighborhood
        payer.city = payerAdapter.city
        payer.state = payerAdapter.state

        payer.save(failOnError: true)
    }

    public void delete(Customer customer, Long id){
        Payer payer = findById(customer, id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Customer customer, Long id, PayerAdapter payerAdapter){
        validateParams(payerAdapter)

        Payer payer = findById(customer, id)
        payer.name = payerAdapter.name
        payer.email = payerAdapter.email
        if (payer.cpfCnpj != payerAdapter.cpfCnpj){
            validateCpfCnpj(customer, payerAdapter.cpfCnpj)
            payer.cpfCnpj = payerAdapter.cpfCnpj
        }
        payer.phoneNumber = payerAdapter.phoneNumber

        payer.postalCode = payerAdapter.postalCode
        payer.streetName = payerAdapter.streetName
        payer.buildingNumber = payerAdapter.buildingNumber
        if (payerAdapter.complement) payer.complement = payerAdapter.complement
        payer.neighborhood = payerAdapter.neighborhood
        payer.city = payerAdapter.city
        payer.state = payerAdapter.state

        payer.save(failOnError: true)
    }

    public void restore(Customer customer, Long id) {
        Payer payer = Payer.query([customer: customer, id: id, onlyDeleted: true]).get()

        if (!payer) throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Pagador']))

        payer.deleted = false
        payer.save(failOnError: true)
    }

    private void validateParams(PayerAdapter payerAdapter) {
        if (!payerAdapter.name) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Nome']))
        }
        if (!payerAdapter.email){
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Endereço de email']))
        }
        if (!(new EmailValidator(false).isValid(payerAdapter.email))) {
            throw new BusinessException(MessageUtils.getMessage('default.invalid.message', ['Endereço de email']))
        }
        if (!payerAdapter.phoneNumber) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Número de telefone']))
        }
        if (!payerAdapter.postalCode) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['CEP']))
        }
        if (!payerAdapter.streetName){
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Nome da rua']))
        }
        if (!payerAdapter.buildingNumber) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Número da residência']))
        }
        if (!payerAdapter.neighborhood) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Bairro']))
        }
        if (!payerAdapter.city) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Cidade']))
        }
        if (!payerAdapter.state) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Estado']))
        }

    }

    private void validateCpfCnpj(Customer customer, String cpfCnpj) {
        if (!cpfCnpj) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['CPF/CNPJ']))
        }
        if (!CpfCnpjValidator.validate(cpfCnpj)) {
            throw new BusinessException(MessageUtils.getMessage('default.invalid.message', ['CPF/CNPJ']))
        }
        if (Payer.query([exists: true, cpfCnpj: cpfCnpj, customer: customer]).get().asBoolean()) {
            throw new BusinessException(MessageUtils.getMessage('default.alreadyExists.message', ['CPF/CNPJ']))
        }
    }
}
