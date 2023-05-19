package payer

import grails.gorm.transactions.Transactional

import java.time.LocalDate

@Transactional
class PayerService {

    public List<Payer> findAll(){
        return Payer.list()
    }

    public Payer findById(Long id){
        return Payer.get(id)
    }

    public void save(params){

        if (!params.name) throw new RuntimeException("Nome é obrigatório")
        if (!params.email) throw new RuntimeException("Email é obrigatório")
        if (!params.cpfCnpj) throw new RuntimeException("CPF/CNPJ é obrigatório")
        if (!params.phoneNumber) throw new RuntimeException("Celular é obrigatório")

        Payer payer = new Payer()
        payer.name = params.name
        payer.email = params.email
        payer.cpfCnpj = params.cpfCnpj
        payer.phoneNumber = params.phoneNumber
        payer.save()
    }
}
