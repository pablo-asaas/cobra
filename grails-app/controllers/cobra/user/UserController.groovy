package cobra.user

import cobra.customer.Customer
import cobra.exception.BusinessException
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class UserController {

    UserService userService
    SpringSecurityService springSecurityService

    def index() {
        return [userList: userService.findAll(getCurrentCustomer()), currentUser: springSecurityService.getCurrentUser().username]
    }

    def save() {
        try {
            userService.save(getCurrentCustomer(), params)
            render([message: "Usuário criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    private Customer getCurrentCustomer() {
        User user = springSecurityService.currentUser
        return user.customer
    }
}
