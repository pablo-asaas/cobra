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
import cobra.util.MessageUtils
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
            throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Cobrança']))
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
            throw new BusinessException(MessageUtils.getMessage('Payment.updatePaidPayment.message', null))
        }

        if (paymentAdapter.dueDate) {
            if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
                throw new BusinessException(MessageUtils.getMessage('Payment.dueDate.priorToEndOfDay.message', null))
            }

            payment.dueDate = paymentAdapter.dueDate
        }

        if (paymentAdapter.value) {
            if (paymentAdapter.value < Payment.PAYMENT_MINIMUM_VALUE) {
                throw new BusinessException(MessageUtils.getMessage('Payment.value.lesserThanMinimumValue.message', null))
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
            throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Cobrança']))
        }

        if (!paymentAdapter.dueDate) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Data de vencimento']))
        }

        if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException(MessageUtils.getMessage('Payment.dueDate.priorToEndOfDay.message', null))
        }

        payment.deleted = false
        payment.dueDate = paymentAdapter.dueDate
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
            throw new ResourceNotFoundException(MessageUtils.getMessage('default.not.found.message', ['Comprovante']))
        }

        return payment
    }

    public confirmDeposit(Customer customer, Long id) {
        Payment payment = findById(customer, id)

        if (payment.status != PaymentStatus.PENDING) {
            throw new BusinessException(MessageUtils.getMessage('Payment.notPendingDeposit.message', null))
        }

        payment.status = PaymentStatus.PAID
        payment.paymentDate = new Date()
        payment.save(failOnError: true)

        paymentNotificationService.onPaid(payment)
    }

    private void validateSaveParams(Customer customer, SavePaymentAdapter paymentAdapter) {
        if (!customer) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Cliente']))
        }

        if (!paymentAdapter.payerId) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Pagador']))
        }

        if (!paymentAdapter.type) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Tipo de pagamento']))
        }

        if (!paymentAdapter.value) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Valor']))
        }

        if (paymentAdapter.value < Payment.PAYMENT_MINIMUM_VALUE) {
            throw new BusinessException(MessageUtils.getMessage('Payment.value.lesserThanMinimumValue.message', null))
        }

        if (!paymentAdapter.dueDate) {
            throw new BusinessException(MessageUtils.getMessage('default.mandatory.message', ['Data de vencimento']))
        }

        if (paymentAdapter.dueDate <= DateUtils.getEndOfDay()) {
            throw new BusinessException(MessageUtils.getMessage('Payment.dueDate.priorToEndOfDay.message', null))
        }
    }
}
