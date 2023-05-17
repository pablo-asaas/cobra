package cobra

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class BaseDomainSpec extends Specification implements DomainUnitTest<BaseDomain> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
