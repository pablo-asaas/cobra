package cobra.user

import cobra.base.BaseController
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import io.micronaut.http.HttpStatus

@Secured('ROLE_USER')
class UserController extends BaseController {

    UserService userService

    def index() {
        return [userList: userService.findAll(getCurrentCustomer()), currentUser: springSecurityService.getCurrentUser().username]
    }

    def save() {
        userService.save(getCurrentCustomer(), params)
        render([message: "Usuário criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
    }
}
