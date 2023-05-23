package cobra.base

import cobra.base.BaseDomain

abstract class BasePerson extends BaseDomain {

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
