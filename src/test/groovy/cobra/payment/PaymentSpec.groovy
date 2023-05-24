package cobra.payment

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.text.SimpleDateFormat

class PaymentSpec extends Specification implements DomainUnitTest<Payment> {

    void "Payment [dueDate] cannot be prior to [createdAt]"() {
        when:
        domain.createdAt = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-02")
        domain.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01")

        then:
        !domain.validate(["dueDate"])
    }

    void "Payment [dueDate] cannot be the same as [createdAt]"() {
        when:
        domain.createdAt = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01")
        domain.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01")

        then:
        !domain.validate(["dueDate"])
    }

    void "Payment [dueDate] must be after [createdAt]"() {
        when:
        domain.createdAt = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01")
        domain.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-02")

        then:
        domain.validate(["dueDate"])
    }

    void "Payment [value] cannot be lower than zero"() {
        when:
        domain.value = new BigDecimal("-1.0")

        then:
        !domain.validate(["value"])
    }

    void "Payment [value] cannot be equals to zero"() {
        when:
        domain.value = new BigDecimal("0")

        then:
        !domain.validate(["value"])
    }

    void "Payment [value] must be greater than zero"() {
        when:
        domain.value = new BigDecimal("1.0")

        then:
        domain.validate(["value"])
    }
}