package cobra.authentication

import cobra.exception.BusinessException
import cobra.user.UserService
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService
import io.micronaut.http.HttpStatus
import org.springframework.security.access.annotation.Secured

@Secured('permitAll')
class RegisterController {

    SpringSecurityService springSecurityService
    UserService userService

    static allowedMethods = [save: 'POST']

    def index() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
        }
        else {
            redirect action: 'create'
        }
    }

    def create() {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: 'index'
            return
        }
        return [:]
    }

    def save() {
        try {
            userService.save(params)
            render([message: "Usuário criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }
}
