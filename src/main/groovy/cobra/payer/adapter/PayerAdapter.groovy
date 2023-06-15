package cobra.payer.adapter

import cobra.payer.Payer

class PayerAdapter {

    String name
    String email
    String cpfCnpj
    String phoneNumber

    String postalCode
    String streetName
    String buildingNumber
    String complement
    String neighborhood
    String city
    String state

    public PayerAdapter(Map params) {
        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.phoneNumber = params.phoneNumber
        this.postalCode = params.postalCode
        this.streetName = params.streetName
        this.buildingNumber = params.buildingNumber
        this.complement = params.complement
        this.neighborhood = params.neighborhood
        this.city = params.city
        this.state = params.state
    }
}
