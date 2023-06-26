package cobra.payment

import cobra.customer.Customer
import cobra.exception.BusinessException
import cobra.exception.ResourceNotFoundException
import cobra.payer.Payer
import cobra.payer.PayerService
import cobra.payment.adapter.RestorePaymentAdapter
import cobra.payment.adapter.SavePaymentAdapter
import cobra.payment.adapter.UpdatePaymentAdapter
import cobra.util.DateUtils
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

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

    public void save(Customer customer, SavePaymentAdapter paymentAdapter) {
        validateSaveParams(customer, paymentAdapter)

        Payment payment = new Payment()
        Payer payer = payerService.findById(customer, paymentAdapter.payerId)

        payment.customer = customer
        payment.payer = payer
        payment.type = paymentAdapter.type
        payment.value = paymentAdapter.value
        payment.dueDate = paymentAdapter.dueDate

        payment.save(failOnError: true)

        paymentNotificationService.onSave(payment)
    }

    public void update(Customer customer, Long id, UpdatePaymentAdapter paymentAdapter) {
        Payment payment = findById(customer, id)

        if (payment.status == PaymentStatus.PAID) {
            throw new BusinessException("Não é possível alterar um pagamento que já esteja pago")
        }

        if (paymentAdapter.dueDate) {
            if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
                throw new BusinessException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
            }

            payment.dueDate = paymentAdapter.dueDate
        }

        if (paymentAdapter.value) {
            if (paymentAdapter.value < Payment.PAYMENT_MINIMUM_VALUE) {
                throw new BusinessException("Não é possível alterar o valor para menor ou igual a zero")
            }

            payment.value = paymentAdapter.value
        }

        if (payment.status == PaymentStatus.OVERDUE) {
            payment.status = PaymentStatus.PENDING
        }

        payment.save(failOnError: true)

        paymentNotificationService.onUpdate(payment)
    }

    public void delete(Customer customer, Long id) {
        Payment payment = findById(customer, id)
        payment.deleted = true

        payment.save(failOnError: true)

        paymentNotificationService.onDelete(payment)
    }

    public void restore(Customer customer, Long id, RestorePaymentAdapter paymentAdapter) {
        Payment payment = Payment.query([customer: customer, id: id, onlyDeleted: true]).get()

        if (!payment) {
            throw new ResourceNotFoundException("Cobrança não encontrada")
        }

        if (!paymentAdapter.dueDate) {
            throw new BusinessException("É obrigatório informar uma nova data de vencimento")
        }

        if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException("Não é possível alterar a data de vencimento para uma data que já esteja vencida")
        }

        payment.deleted = false
        payment.dueDate = paymentAdapter.dueDate
        payment.status = PaymentStatus.PENDING

        payment.save(failOnError: true)

        paymentNotificationService.onRestore(payment)
    }

    public void processToOverdue() {
        List<Long> paymentIdList = Payment.query(column: "id", "dueDate[lt]": DateUtils.getStartOfDay(),
                ignoreCustomer: true,
                status: PaymentStatus.PENDING).list() as List<Long>

        for (Long paymentId : paymentIdList) {
            try {
                Payment.withNewTransaction { status ->
                    Payment payment = Payment.get(paymentId)
                    payment.status = PaymentStatus.OVERDUE
                    payment.save(failOnError: true)

                    paymentNotificationService.onOverdue(payment)
                }
            } catch (Exception exception) {
                println("Falha ao processar vencimento da cobrança ${paymentId}: ${exception.getMessage()}")
            }
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

    public confirmDeposit(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status != PaymentStatus.PENDING) {
            throw new BusinessException("Só é possível confirmar um pagamento se estiver pendente")
        }

        payment.status = PaymentStatus.PAID
        payment.paymentDate = new Date()
        payment.save(failOnError: true)

        paymentNotificationService.onPaid(payment)
    }

    private void validateSaveParams(Customer customer, SavePaymentAdapter paymentAdapter) {
        if (!customer) {
            throw new BusinessException("É obrigatório informar um cliente")
        }

        if (!paymentAdapter.payerId) {
            throw new BusinessException("É obrigatório informar um pagador")
        }

        if (!paymentAdapter.type) {
            throw new BusinessException("É obrigatório informar um tipo")
        }

        if (!paymentAdapter.value) {
            throw new BusinessException("É obrigatório informar um valor")
        }

        if (paymentAdapter.value < Payment.PAYMENT_MINIMUM_VALUE) {
            throw new BusinessException("O valor não pode ser menor ou igual a zero")
        }

        if (!paymentAdapter.dueDate) {
            throw new BusinessException("É obrigatório informar uma data de vencimento")
        }

        if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException("A data de vencimento não pode ser anterior ou igual ao dia de hoje")
        }
    }
}
