package cobra.base

import cobra.base.BaseDomain
import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class BasePerson extends BaseDomain {

    String name
    String email
    String cpfCnpj

    String postalCode
    String streetName
    String buildingNumber
    String complement
    String neighborhood
    String city
    String state

    static mapping = {
        cpfCnpj unique: true
    }

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
        postalCode blank: false, size: 8..8
        streetName blank: false
        buildingNumber blank: false
        complement nullable: true
        neighborhood blank: false
        city blank: false
        state blank: false
    }
}
