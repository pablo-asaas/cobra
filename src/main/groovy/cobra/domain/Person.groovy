package cobra.domain

class Person {

    String name
    String email
    String cpfCnpj

    static mapping = {
        cpfCnpj unique: true
    }

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
    }
}
