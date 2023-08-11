package SI_Recurring_Payment.model;

import SI_Recurring_Payment.utils.enums.TxnStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long txnId;
    private Long siId;
    private Long invoiceNo;
    private Long merchantId;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private TxnStatus authStatus;
    private Date createdOn;
}
