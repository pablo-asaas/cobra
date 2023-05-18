package cobra.domain

class Person {

    String name
    String email
    String cpfCnpj

    static mapping = {
        cpfCnpj unique: true
    }
}
