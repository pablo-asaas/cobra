package cobra.base

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.user.User
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import io.micronaut.http.HttpStatus

class BaseController {

    SpringSecurityService springSecurityService

    protected final DEFAULT_PAGINATION_LIMIT = 10

    def handleBusinessException(BusinessException businessException) {
        render([message: businessException.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
    }

    def handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        render(view: "/notFound", model: [message: resourceNotFoundException.message], status: HttpStatus.NOT_FOUND.code)
    }

    def handleException(Exception exception) {
        exception.printStackTrace()
        render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.INTERNAL_SERVER_ERROR.code)
    }

    protected Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
