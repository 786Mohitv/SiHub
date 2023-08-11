package SI_Recurring_Payment.repository;

import SI_Recurring_Payment.model.StandingInstruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SiRepo extends JpaRepository<StandingInstruction,Long> {

    @Query(value = "select * from standing_instruction where merchant_id=?1",nativeQuery = true)
    Optional<List<StandingInstruction>> getSiByMerchantId(Long id);


}
