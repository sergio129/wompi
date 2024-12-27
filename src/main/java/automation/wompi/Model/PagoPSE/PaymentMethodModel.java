package automation.wompi.Model.PagoPSE;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentMethodModel {
    private String type;
    private String user_type;
    private String user_legal_id_type;
    private String user_legal_id;
    private String financial_institution_code;
    private String payment_description;
}
