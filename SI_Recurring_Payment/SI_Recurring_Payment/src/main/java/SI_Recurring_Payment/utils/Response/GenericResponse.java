package SI_Recurring_Payment.utils.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenericResponse {
    private String status;
    private String statusCode;
    private String message;
}
