package payer

class Payer {

    String name
    String email
    String cpfCnpj

    static mapping = {
        cpfCnpj unique: true, length: 14
    }
    static constraints = {
        name blank: false
        email blank: false, email: true
    }
}
