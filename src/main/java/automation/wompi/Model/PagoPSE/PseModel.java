package automation.wompi.Model.PagoPSE;

import lombok.Builder;
import lombok.Data;

import java.util.PrimitiveIterator;

@Builder
@Data
public class PseModel {
    private String redirect_url;
    private int  amount_in_cents;
    private String reference;
    private String currency;
    private String signature;
    private CustomerdataModel customer_data;

    private String customer_email;
    private String merchant_user_id;
    private String session_id;
    private String payment_method_type;
    private String customer_number_prefix;
    private PaymentMethodModel payment_method;
    private String acceptance_token;
    private String accept_personal_auth;

}
