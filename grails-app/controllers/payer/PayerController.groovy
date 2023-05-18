package payer

class PayerController {

    def payerService

    static allowedMethods = [index: 'GET', save: 'POST']

    def newPayer(){}

    def index() {
        return [params: payerService.findAll()]
    }

    def save(params) {
        try {
            payerService.save(params)
        }catch (Exception e) {
            Map message = [message: e.message]
            respond message, formats: ['json']
            return
        }
        redirect action: "index"
    }
}
