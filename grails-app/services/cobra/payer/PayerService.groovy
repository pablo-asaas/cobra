package cobra.payer

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.validator.CpfCnpjValidator
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

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


    public void save(Customer customer, Map params){
        validateParams(params, customer)

        Payer payer = new Payer()
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber
        payer.customer = customer

        payer.save(failOnError: true)
    }

    public void delete(Customer customer, Long id){
        Payer payer = findById(customer, id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Customer customer, Long id, Map params){
        validateParams(params, customer)

        Payer payer = findById(customer, id)
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber

        payer.save(failOnError: true)
    }

    public void restore(Customer customer, Long id) {
        Payer payer = Payer.query([customer: customer, id: id, onlyDeleted: true]).get()

        if (!payer) throw new ResourceNotFoundException("Pagador não encontrado")

        payer.deleted = false
        payer.save(failOnError: true)
    }

    private void validateParams(Map params, Customer customer) {
        if (!params.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if(!params.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!params.phoneNumber) {
            throw new BusinessException("Numero de Telefone é obrigatório")
        }
        validateCpfCnpj(params.cpfCnpj, customer)
    }

    private void validateCpfCnpj(String cpfCnpj, Customer customer) {
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
