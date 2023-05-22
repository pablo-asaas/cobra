package payer

import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    public List<Payer> findAll(){
        return Payer.list()
    }

    public Payer findById(Long id){
        return Payer.get(id)
    }

    public void save(Map params){

        Payer payer = new Payer()
        println params

        if (params.name) payer.name = params.name
        if (params.email) payer.email = params.email
        if (params.cpfCnpj) payer.cpfCnpj = params.cpfCnpj
        if (params.phoneNumber) payer.phoneNumber = params.phoneNumber

        payer.save(failOnError: true)
    }
}
