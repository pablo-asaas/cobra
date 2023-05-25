package cobra.payer

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
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
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
            redirect action: "index"
        }catch (Exception e) {
            e.printStackTrace()
            redirect action: "index"
        }
    }
}
