package cobra.user

import cobra.base.BaseController
import cobra.util.MessageUtils
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
        render([message: MessageUtils.getMessage('default.created.message', ['Usuário'])] as JSON,
                status: HttpStatus.CREATED.code)
    }
}
