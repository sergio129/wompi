package automation.wompi.Builders;

import automation.wompi.Interaction.AutenticacionInteraction;
import automation.wompi.Model.PagoPSE.CustomerdataModel;
import automation.wompi.Model.PagoPSE.PaymentMethodModel;
import automation.wompi.Model.PagoPSE.PseModel;
import automation.wompi.Utilities.DatosPagoPSE;
import automation.wompi.Utilities.SignatureGenerator;
import automation.wompi.Utilities.ReferenceGenerator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PseBuild {
  private final PseModel model;
    public static PseModel Principal() {
        final PseModel model = null;

        // Generar referencia única
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");

        // Generar la firma con la referencia única
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
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

    // MÉTODOS PARA ESCENARIOS ALTERNOS

    public static PseModel sinFirma() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(null) // Sin firma
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

    public static PseModel conFirmaInvalida() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature("firma_invalida_12345") // Firma incorrecta
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

    public static PseModel conMontoNegativo() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = -1000;
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents) // Monto negativo
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
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

    public static PseModel conMontoMinimo() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = 100; // Monto muy bajo
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
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

    public static PseModel sinEmail() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email(null) // Sin email
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(Payment_method())
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel conEmailInvalido() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email("email-invalido") // Email sin formato válido
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(Payment_method())
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel sinMetodoPago() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email(DatosPagoPSE.getDatosPagoPSE("pago.customer_email"))
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(null) // Sin método de pago
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel sinTokenAceptacion() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email(DatosPagoPSE.getDatosPagoPSE("pago.customer_email"))
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(Payment_method())
                .acceptance_token(null) // Sin token de aceptación
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel sinCodigoBanco() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        PaymentMethodModel paymentMethodSinBanco = PaymentMethodModel.builder()
                .type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .user_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_type"))
                .user_legal_id_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_legal_id_type"))
                .user_legal_id(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_legal_id"))
                .financial_institution_code(null) // Sin código de banco
                .payment_description(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.payment_description"))
                .build();

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email(DatosPagoPSE.getDatosPagoPSE("pago.customer_email"))
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(paymentMethodSinBanco)
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel conDocumentoInvalido() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        PaymentMethodModel paymentMethodDocInvalido = PaymentMethodModel.builder()
                .type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .user_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_type"))
                .user_legal_id_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.user_legal_id_type"))
                .user_legal_id("ABC123XYZ") // Documento inválido
                .financial_institution_code(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.financial_institution_code"))
                .payment_description(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.payment_description"))
                .build();

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
                .customer_data(Customer_data())
                .customer_email(DatosPagoPSE.getDatosPagoPSE("pago.customer_email"))
                .merchant_user_id(DatosPagoPSE.getDatosPagoPSE("pago.merchant_user_id"))
                .session_id(DatosPagoPSE.getDatosPagoPSE("pago.session_id"))
                .payment_method_type(DatosPagoPSE.getDatosPagoPSE("pago.payment_method.type"))
                .customer_number_prefix(DatosPagoPSE.getDatosPagoPSE("pago.customer_number_prefix"))
                .payment_method(paymentMethodDocInvalido)
                .acceptance_token(AutenticacionInteraction.TokenAcceptance())
                .accept_personal_auth(AutenticacionInteraction.TokenPersonal())
                .build();
    }

    public static PseModel conMonedaInvalida() {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = "USD"; // Moneda no soportada
        String signature = SignatureGenerator.generateSignature(uniqueReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(uniqueReference)
                .currency(currency) // USD en lugar de COP
                .signature(signature)
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

    public static PseModel conMonto(int monto) {
        String uniqueReference = ReferenceGenerator.generateUniqueReference();
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(uniqueReference, monto, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(monto) // Monto personalizado
                .reference(uniqueReference)
                .currency(currency)
                .signature(signature)
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

    public static PseModel conReferenciaFija() {
        String fixedReference = "REF_DUPLICADA_TEST_001";
        int amountInCents = Integer.parseInt(DatosPagoPSE.getDatosPagoPSE("pago.amount_in_cents"));
        String currency = DatosPagoPSE.getDatosPagoPSE("pago.currency");
        String signature = SignatureGenerator.generateSignature(fixedReference, amountInCents, currency);

        return PseModel.builder()
                .redirect_url(DatosPagoPSE.getDatosPagoPSE("pago.redirect_url"))
                .amount_in_cents(amountInCents)
                .reference(fixedReference) // Referencia fija para duplicar
                .currency(currency)
                .signature(signature)
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
}
