package SI_Recurring_Payment.repository;

import SI_Recurring_Payment.model.Invoice;
import SI_Recurring_Payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxnRepo extends JpaRepository<Transaction,Long> {
}
