package SI_Recurring_Payment.repository;

import SI_Recurring_Payment.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepo extends JpaRepository<Merchant,Long> {
}
