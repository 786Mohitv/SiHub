package SI_Recurring_Payment.model;

import SI_Recurring_Payment.utils.enums.AmountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandingInstruction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long siId;

    @Column(nullable = false)
    private Long merchantId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AmountType amountType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tokenId",referencedColumnName = "id",nullable = false)
    private Token token;

    private String emailId;

    private String mNumber;

    private Boolean status;

    @Column(nullable = false)
    private Date createdOn;

    private Date modifiedOn;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String subscriptionId;
}
