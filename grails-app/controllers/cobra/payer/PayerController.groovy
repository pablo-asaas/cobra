package cobra.payer

import cobra.exception.BusinessException
import grails.converters.JSON
import io.micronaut.http.HttpStatus

class PayerController {

    def payerService

    static allowedMethods = [index: 'GET', save: 'POST', update: 'PUT']

    def create() {
        return [:]
    }

    def index() {
        return [payerList: payerService.findAll()]
    }

    def save() {
        try {
            payerService.save(params)
            render([message: "Criado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }

    def delete(Long id) {
        try {
            payerService.delete(id)
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }

    def show (Long id) {
        return [payer: payerService.findById(id)]
    }

    def update() {
        try {
            payerService.update(params.id as Long, params)
            render([message: "Editado com sucesso"] as JSON, status: HttpStatus.CREATED.code)
        }catch (BusinessException e) {
            e.printStackTrace()
            render([message: e.message] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }catch (Exception e){
            e.printStackTrace()
            render([message: "Ocorreu um erro desconhecido"] as JSON, status: HttpStatus.BAD_REQUEST.code)
        }
    }
}
