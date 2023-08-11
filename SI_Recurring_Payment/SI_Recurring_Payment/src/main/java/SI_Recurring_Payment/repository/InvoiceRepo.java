package SI_Recurring_Payment.repository;


import SI_Recurring_Payment.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice,Long> {
    @Query(value = "select * from invoice where si_id=?1 and merchant_id=?2",nativeQuery = true)
    Optional<Invoice> findBySiIdAndMerchantId(Long siId,Long merchantId);
}
