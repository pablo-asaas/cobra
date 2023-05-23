package cobra.payment

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@Transactional
class PaymentService {

    @ReadOnly
    public List<Payment> findAll() { }

    @ReadOnly
    public Payment findById() { }

    public void save() { }

    public void update() { }

    public void delete() { }
}
