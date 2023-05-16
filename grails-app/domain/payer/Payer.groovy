package payer

import java.time.LocalDate

class Payer {

    Long id
    String name
    String email
    String celular
    String cpfCnpj
    boolean deleted
    LocalDate createdAt

    static mapping = {
        id generator: 'identity'
        cpfCnpj unique: true, length: 14
        version(false)
    }
    static constraints = {
        name blank: false
        email blank: false
    }
}
