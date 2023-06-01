package cobra.payer

import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    @ReadOnly
    public List<Payer> findAll(){
        return Payer.query([:]).list()
    }

    public Payer findById(Long id){
        Payer payer = Payer.query([id: id]).get()

        if (!payer) throw new ResourceNotFoundException("Pagador não encontrado")

        return payer
    }

    public void save(Map params){
        validateParams(params)

        Payer payer = new Payer()
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber

        payer.save(failOnError: true)
    }

    public void delete(Long id){
        Payer payer = findById(id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Long id, Map params){
        validateParams(params)

        Payer payer = findById(id)
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber

        payer.save(failOnError: true)
    }

    private void validateParams(Map params) {
        if (!params.name) {
            throw new BusinessException("Nome é obrigatório")
        }
        if(!params.email){
            throw new BusinessException("Email é obrigatório")
        }
        if (!params.cpfCnpj) {
            throw new BusinessException("Cpf/Cnpj é obrigatório")
        }
        if (!params.phoneNumber) {
            throw new BusinessException("Numero de Telefone é obrigatório")
        }
    }
}
