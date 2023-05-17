package cobra

import java.time.LocalDate

abstract class BaseDomain {

    LocalDate createdAt
    boolean deleted

    static mapping = {
        tablePerConcreteClass true
        version false
    }
}
