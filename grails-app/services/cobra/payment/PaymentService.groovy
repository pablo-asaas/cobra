package cobra.payment

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.payer.Payer
import cobra.payer.PayerService
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    PayerService payerService

    @ReadOnly
    public List<Payment> findAll(Customer customer) {
        return Payment.query([customer: customer]).list()
    }

    public Payment findById(Customer customer, Long id) {
        Payment payment = Payment.query([customer: customer, id: id]).get()

        if (!payment) {
            throw new RuntimeException("Pagamento não encontrado")
        }

        return payment
    }

    public void save(Customer customer, Map params) {
        validateSaveParams(customer, params)

        Payment payment = new Payment()
        Payer payer = payerService.findById(params.payer as Long)

        payment.customer = customer
        payment.payer = payer
        payment.type = PaymentType.valueOf(params.type.toUpperCase())
        payment.value = new BigDecimal(params.value)
        payment.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

        payment.save(failOnError: true)
    }

    public void update(Customer customer, Long id, Map params) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Não é possível alterar um pagamento que já esteja pago")
        }

        if (params.dueDate) {
            Date updatedDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

            if (updatedDueDate <= new Date()) {
                throw new BusinessException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
            }

            payment.dueDate = updatedDueDate
        }

        if (params.value) {
            payment.value = new BigDecimal(params.value)
        }

        payment.save(failOnError: true)
    }

    public void delete(Customer customer, Long id) {
        Payment payment = findById(customer, id)
        payment.deleted = true
        payment.save(failOnError: true)
    }

    public void setPendingStatus(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PENDING) {
            throw new BusinessException("Este pagamento já está pendente")
        }

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Não é possível definir como pendente um pagamento que esteja pago")
        }

        payment.status = PaymentStatus.PENDING
        payment.save(failOnError: true)
    }

    public void setPaidStatus(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Este pagamento já está pago")
        }

        payment.status = PaymentStatus.PAID
        payment.save(failOnError: true)
    }

    public void setOverdueStatus(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.OVERDUE) {
            throw new BusinessException("Este pagamento já está vencido")
        }

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Não é possível definir como vencido um pagamento que esteja pago")
        }

        if (new Date() < payment.dueDate) {
            throw new BusinessException("Este pagamento ainda não atingiu a data de vencimento")
        }

        payment.status = PaymentStatus.OVERDUE
        payment.save(failOnError: true)
    }

    private void validateSaveParams(Customer customer, Map params) {
        if (!customer) {
            throw new BusinessException("É obrigatório informar um cliente")
        }

        if (!params.payer) {
            throw new BusinessException("É obrigatório informar um pagador")
        }

        if (!params.type) {
            throw new BusinessException("É obrigatório informar um tipo")
        }

        if (!params.value) {
            throw new BusinessException("É obrigatório informar valor")
        }

        if (!params.dueDate) {
            throw new BusinessException("É obrigatório informar uma data de vencimento")
        }
    }
}
