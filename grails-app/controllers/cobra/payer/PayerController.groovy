package cobra.payer

class PayerController {

    def payerService

    static allowedMethods = [index: 'GET', save: 'POST']

    def create() {
        return [:]
    }

    def index() {
        return [params: payerService.findAll()]
    }

    def save() {
        try {
            payerService.save(params)
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "create"
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
}
