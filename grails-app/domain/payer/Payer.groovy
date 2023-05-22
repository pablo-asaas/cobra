package payer

import cobra.domain.BasePerson

class Payer extends BasePerson{

    String phoneNumber

    static constraints = {
        phoneNumber blank: false, size: 13..13
    }
}
