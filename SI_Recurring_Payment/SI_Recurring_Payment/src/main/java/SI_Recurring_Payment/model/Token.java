package SI_Recurring_Payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tokenNumber;
    private Integer expiryMonth;
    private Integer expiryDay;
    private String tokenBin;
    private Date createdOn;
    private Date modifiedOn;
}
