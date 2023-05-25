package cobra.payment

import cobra.customer.Customer
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

    @ReadOnly
    public Payment findById(Customer customer, Long id) {
        Payment payment = Payment.query([customer: customer, id: id]).get()

        if (!payment) throw new RuntimeException("Pagamento não encontrado")

        return payment
    }

    public void save(Customer customer, Map params) {
        Payment payment = new Payment()
        Payer payer = payerService.findById(params.payer as Long);

        if (params.customer) payment.customer = customer
        if (params.payer) payment.payer = payer
        if (params.type) payment.type = PaymentType.valueOf(params.type.toUpperCase())
        if (params.value) payment.value = new BigDecimal(params.value)
        if (params.dueDate) payment.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

        payment.save(failOnError: true)
    }

    public void update(Customer customer, Long id, Map params) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new RuntimeException("Não é possível alterar um pagamento que já esteja pago")
        }

        if (payment.status == PaymentStatus.PENDING) updatePendingPayment(payment, params)
        if (payment.status == PaymentStatus.OVERDUE) updateOverduePayment(payment, params)

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
            throw new RuntimeException("Este pagamento já está pendente")
        }

        if (payment.status == PaymentStatus.PAID) {
            throw new RuntimeException("Não é possível definir como pendente um pagamento que esteja pago")
        }

        payment.status = PaymentStatus.PENDING
        payment.save(failOnError: true)
    }

    public void setPaidStatus(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new RuntimeException("Este pagamento já está pago")
        }

        if (payment.status == PaymentStatus.OVERDUE) {
            throw new RuntimeException("Não é possível definir como pago um pagamento que esteja vencido")
        }

        payment.status = PaymentStatus.PAID
        payment.save(failOnError: true)
    }

    public void setOverdueStatus(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.OVERDUE) {
            throw new RuntimeException("Este pagamento já está vencido")
        }

        if (payment.status == PaymentStatus.PAID) {
            throw new RuntimeException("Não é possível definir como vencido um pagamento que esteja pago")
        }

        payment.status = PaymentStatus.OVERDUE
        payment.save(failOnError: true)
    }

    private void updatePendingPayment(Payment payment, Map params) {
        if (params.dueDate) payment.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)
        if (params.value) payment.value = new BigDecimal(params.value)
    }

    private void updateOverduePayment(Payment payment, Map params) {
        if (params.dueDate) {
            if (params.dueDate <= payment.dueDate) {
                throw new RuntimeException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
            }

            payment.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)
        }
    }
}
