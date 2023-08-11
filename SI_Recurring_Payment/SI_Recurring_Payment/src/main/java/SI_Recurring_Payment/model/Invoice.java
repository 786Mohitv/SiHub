package SI_Recurring_Payment.model;

import SI_Recurring_Payment.utils.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceNo;
    private Long siId;
    private Double amount;
    private Date debitDate;
    private Date dueDate;
    private Long merchantId;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
    private Date createOn;
    private Date modifiedOn;
}
