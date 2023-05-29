package cobra.payer

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    @ReadOnly
    public List<Payer> findAll(){
        return Payer.query([:]).list()
    }

    @ReadOnly
    public Payer findById(Long id){
        Payer payer = Payer.query([id: id]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        return payer
    }

    public void save(Map params){
        Payer payer = new Payer()
        bind(payer, params)
        payer.save(failOnError: true)
    }

    public void delete(Long id){
        Payer payer = findById(id)
        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void update(Long id, Map params){
        Payer payer = findById(id)
        bind(payer, params)
        payer.save(failOnError: true)
    }

    private void bind(Payer payer, Map params){
        if (params.name) payer.name = params.name
        if (params.email) payer.email = params.email
        if (params.cpfCnpj) payer.cpfCnpj = params.cpfCnpj
        if (params.phoneNumber) payer.phoneNumber = params.phoneNumber
    }
}
