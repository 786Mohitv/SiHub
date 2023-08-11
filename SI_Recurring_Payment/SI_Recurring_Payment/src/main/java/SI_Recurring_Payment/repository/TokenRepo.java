package SI_Recurring_Payment.repository;

import SI_Recurring_Payment.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token,Long> {
}
