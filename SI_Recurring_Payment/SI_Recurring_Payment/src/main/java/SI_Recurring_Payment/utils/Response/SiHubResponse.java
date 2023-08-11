package SI_Recurring_Payment.utils.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SiHubResponse<T> {
    private T response;
    private GenericResponse responseMetaData;
}
