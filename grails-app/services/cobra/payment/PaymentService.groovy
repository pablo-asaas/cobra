package cobra.payment

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.Payer
import cobra.payer.PayerService
import cobra.util.DateUtils
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

import java.text.SimpleDateFormat

@Transactional
class PaymentService {

    PayerService payerService
    PaymentNotificationService paymentNotificationService

    @ReadOnly
    public List<Payment> findAll(Customer customer) {
        return Payment.query([customer: customer]).list()
    }

    @ReadOnly
    public List<Payment> findAllDeleted(Customer customer) {
        return Payment.query([customer: customer, onlyDeleted: true]).list()
    }

    public Payment findById(Customer customer, Long id) {
        Payment payment = Payment.query([customer: customer, id: id]).get()

        if (!payment) {
            throw new ResourceNotFoundException("Cobrança não encontrada")
        }

        return payment
    }

    public Payment findByPublicId(Customer customer, String publicId) {
        Payment payment = Payment.query([customer: customer, publicId: publicId]).get()

        if (!payment) {
            throw new ResourceNotFoundException("Cobrança não encontrada")
        }

        return payment
    }

    public void save(Customer customer, Map params) {
        validateSaveParams(customer, params)

        Payment payment = new Payment()
        Payer payer = payerService.findById(customer, params.payer as Long)

        payment.customer = customer
        payment.payer = payer
        payment.type = PaymentType.valueOf(params.type.toUpperCase())
        payment.value = new BigDecimal(params.value)
        payment.dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

        payment.save(failOnError: true)

        paymentNotificationService.onSave(payment)
    }

    public void update(Customer customer, Long id, Map params) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Não é possível alterar um pagamento que já esteja pago")
        }

        if (params.dueDate) {
            Date updatedDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

            if (updatedDueDate <= DateUtils.getEndOfDay()) {
                throw new BusinessException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
            }

            payment.dueDate = updatedDueDate
        }

        if (params.value) {
            payment.value = new BigDecimal(params.value)
        }

        if (payment.status == PaymentStatus.OVERDUE) {
            payment.status = PaymentStatus.PENDING
        }

        payment.save(failOnError: true)

        paymentNotificationService.onUpdate(payment)
    }

    public void delete(Customer customer, String publicID) {
        Payment payment = findByPublicId(customer, publicID)
        payment.deleted = true

        payment.save(failOnError: true)

        paymentNotificationService.onDelete(payment)
    }

    public void restore(Customer customer, Long id, Map params) {
        Payment payment = Payment.query([customer: customer, id: id, onlyDeleted: true]).get()

        if (!payment) {
            throw new ResourceNotFoundException("Cobrança não encontrada")
        }

        if (!params.dueDate) {
            throw new BusinessException("É obrigatório informar uma nova data de vencimento")
        }

        Date parsedDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

        if (parsedDueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
        }

        payment.deleted = false
        payment.dueDate = parsedDueDate
        payment.status = PaymentStatus.PENDING

        payment.save(failOnError: true)

        paymentNotificationService.onRestore(payment)
    }

    public void processToOverdue() {
        List<Payment> paymentList = Payment.query("dueDate[lt]": DateUtils.getStartOfDay(),
                                                  ignoreCustomer: true,
                                                  status: PaymentStatus.PENDING).list()

        for (Payment payment : paymentList) {
            payment.status = PaymentStatus.OVERDUE

            try {
                payment.save(failOnError: true)
            } catch (Exception exception) {
                exception.printStackTrace()
            }

            paymentNotificationService.onOverdue(payment)
        }
    }

    @ReadOnly
    public Payment getPaymentReceipt(String publicId) {
        Payment payment = Payment.query([publicId: publicId,
                                         ignoreCustomer: true,
                                         status: PaymentStatus.PAID]).get()

        if (!payment) {
            throw new ResourceNotFoundException("Comprovante não encontrado")
        }

        return payment
    }

    public confirmDeposit(Customer customer, String id) {
        Payment payment = findByPublicId(customer, id)

        if (payment.status != PaymentStatus.PENDING) {
            throw new BusinessException("Só é possível confirmar um pagamento se estiver pendente")
        }

        payment.status = PaymentStatus.PAID
        payment.paymentDate = new Date()
        payment.save(failOnError: true)

        paymentNotificationService.onPaid(payment)
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
            throw new BusinessException("É obrigatório informar um valor")
        }

        BigDecimal parsedValue = new BigDecimal(params.value)

        if (parsedValue <= BigDecimal.ZERO) {
            throw new BusinessException("O valor não pode ser menor ou igual a zero")
        }

        if (!params.dueDate) {
            throw new BusinessException("É obrigatório informar uma data de vencimento")
        }

        Date parsedDueDate = new SimpleDateFormat("yyyy-MM-dd").parse(params.dueDate)

        if (parsedDueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException("A data de vencimento não pode ser anterior ou igual ao dia de hoje")
        }
    }
}
