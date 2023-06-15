package cobra.payer

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.adapter.PayerAdapter
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

        if (!payer) throw new ResourceNotFoundException("Pagador não encontrado")

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

        if (!payer) throw new ResourceNotFoundException("Pagador não encontrado")

        payer.deleted = false
        payer.save(failOnError: true)
    }

    private void validateParams(PayerAdapter payerAdapter) {
        if (!payerAdapter.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if (!payerAdapter.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!(new EmailValidator(false).isValid(payerAdapter.email))) {
            throw new BusinessException("Email inválido")
        }
        if (!payerAdapter.phoneNumber) {
            throw new BusinessException("Numero de Telefone é obrigatório")
        }
        if (!payerAdapter.postalCode) {
            throw new BusinessException("CEP é obrigatório")
        }
        if (!payerAdapter.streetName){
            throw new BusinessException("Nome da Rua é obrigatório")
        }
        if (!payerAdapter.buildingNumber) {
            throw new BusinessException("Número da residência é obrigatório")
        }
        if (!payerAdapter.neighborhood) {
            throw new BusinessException("Bairro é obrigatório")
        }
        if (!payerAdapter.city) {
            throw new BusinessException("Cidade é obrigatório")
        }
        if (!payerAdapter.state) {
            throw new BusinessException("Estado é obrigatório")
        }

    }

    private void validateCpfCnpj(Customer customer, String cpfCnpj) {
        if (!cpfCnpj) {
            throw new BusinessException("CPF/CNPJ é obrigatório")
        }
        if (!CpfCnpjValidator.validate(cpfCnpj)) {
            throw new BusinessException("CPF/CNPJ inválido")
        }
        if (Payer.query([exists: true, cpfCnpj: cpfCnpj, customer: customer]).get().asBoolean()) {
            throw new BusinessException("CPF/CNPJ já cadastrado")
        }
    }
}
