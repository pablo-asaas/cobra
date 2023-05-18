package payer

import grails.gorm.transactions.Transactional

import java.time.LocalDate

@Transactional
class PayerService {

    List<Payer> findAll(){
        return Payer.list()
    }

    Payer findById(Long id){
        return Payer.get(id)
    }

    void save(params){

        if (!params.name)
            throw new RuntimeException("Name is mandatory!")
        if (!params.email)
            throw new RuntimeException("Name is mandatory!")
        if (!params.cpfCnpj)
            throw new RuntimeException("Name is mandatory!")

        Payer payer = new Payer()
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.createdAt = LocalDate.now()
        payer.save()
    }
}
