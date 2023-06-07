package cobra.payer

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
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
        validateParams(params)

        Payer payer = new Payer()
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber
        payer.customer = customer

        payer.postalCode = params.postalCode
        payer.streetName = params.streetName
        payer.buildingNumber = params.buildingNumber
        if (params.complement) payer.complement = params.complement
        payer.neighborhood = params.neighborhood
        payer.city = params.city
        payer.state = params.state

        payer.save(failOnError: true)
    }

    public void delete(Customer customer, Long id){
        Payer payer = findById(customer, id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Customer customer, Long id, Map params){
        validateParams(params)

        Payer payer = findById(customer, id)
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber

        payer.postalCode = params.postalCode
        payer.streetName = params.streetName
        payer.buildingNumber = params.buildingNumber
        if (params.complement) payer.complement = params.complement
        payer.neighborhood = params.neighborhood
        payer.city = params.city
        payer.state = params.state

        payer.save(failOnError: true)
    }

    public void restore(Customer customer, Long id) {
        Payer payer = Payer.query([customer: customer, id: id, onlyDeleted: true]).get()

        if (!payer) throw new ResourceNotFoundException("Pagador não encontrado")

        payer.deleted = false
        payer.save(failOnError: true)
    }

    private void validateParams(Map params) {
        if (!params.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if (!params.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!params.cpfCnpj) {
            throw new BusinessException("Cpf/Cnpj é obrigatório")
        }
        if (!params.phoneNumber) {
            throw new BusinessException("Numero de Telefone é obrigatório")
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
    }
}
