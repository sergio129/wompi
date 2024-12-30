package automation.wompi.Builders;

import automation.wompi.Interaction.AutenticacionInteraction;
import automation.wompi.Model.PagoPSE.CustomerdataModel;
import automation.wompi.Model.PagoPSE.PaymentMethodModel;
import automation.wompi.Model.PagoPSE.PseModel;
import automation.wompi.Utilities.DatosPagoPSE;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PseBuild {
  private final PseModel model;
    public static PseModel Principal() {
        final PseModel model = null;
        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(Integer.parseInt((DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"))))
                .reference(DatosPagoPSE.getDatosPagoPSE("pago.reference"))
                .currency(DatosPagoPSE.getDatosPagoPSE("pago.currency"))
                .signature(DatosPagoPSE.getDatosPagoPSE("pago.signature"))
                .customer_data(Customer_data())
                .customer_email(DatosPagoPSE.getDatosPagoPSE("pago.customer_email"))
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(Payment_method())
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();

    }

    public static CustomerdataModel Customer_data() {
        return CustomerdataModel.builder()
                .phone_number(DatosPagoPSE.getDatosPagoPSE("pago.customer_data.phone_number"))
                .full_name(DatosPagoPSE.getDatosPagoPSE("pago.customer_data.full_name"))
                .device_id(DatosPagoPSE.getDatosPagoPSE("pago.customer_data.device_id"))
                .build();
    }

    public static PaymentMethodModel Payment_method() {
        return PaymentMethodModel.builder()
                .type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .user_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_type"))
                .user_legal_id_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_legal_id_type"))
                .user_legal_id(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_legal_id"))
                .financial_institution_code(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.financial_institution_code"))
                .payment_description(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.payment_description"))
                .build();
    }
}
