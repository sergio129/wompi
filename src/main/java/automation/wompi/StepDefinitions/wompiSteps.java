package automation.wompi.StepDefinitions;

import automation.wompi.Interaction.AutenticacionInteraction;
import automation.wompi.Model.AuthModel;
import automation.wompi.Task.AutenticacionTask;
import automation.wompi.Task.PagoPSETask;
import automation.wompi.Utilities.DatosAuth;
import automation.wompi.questions.ResponseCode;
import automation.wompi.questions.ResponseStatus;
import automation.wompi.questions.ResponsePaymentMethodType;
import automation.wompi.questions.ResponseField;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static automation.wompi.Utilities.Url.UrlBase;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class wompiSteps {
    Actor actor = Actor.named("Wompi");

    @Before
    public void setUp() {
        actor.whoCan(CallAnApi.at(UrlBase));
    }

    @Given("que wompi tiene claves de API válidas")
    public void queJohnTieneClavesDeAPIVálidas() {
        AuthModel model = DatosAuth.llaves();
        actor.attemptsTo(AutenticacionTask.datos(model));
    }

    @When("wompi hace una solicitud para autenticar")
    public void johnHaceUnaSolicitudParaAutenticar() {
        // Solicitud ya realizada en el paso @Dado
    }

    @Then("el estado de la respuesta debería ser {int}")
    public void el_estado_de_la_respuesta_debería_ser(int statusCode) {
        actor.should(seeThat(new ResponseCode(), equalTo(statusCode)));
    }

    @Then("la respuesta debería contener el estado {string}")
    public void la_respuesta_debería_contener_el_estado(String expectedStatus) {
        actor.should(seeThat("el estado de la transacción", ResponseStatus.value(), equalTo(expectedStatus)));
    }

    @Then("la respuesta debería contener el tipo de pago {string}")
    public void la_respuesta_debería_contener_el_tipo_de_pago(String expectedPaymentType) {
        actor.should(seeThat("el tipo de pago", ResponsePaymentMethodType.value(), equalTo(expectedPaymentType)));
    }

    @Then("la respuesta debería contener el error {string}")
    public void la_respuesta_debería_contener_el_error(String expectedError) {
        // Validar que existe el error en la estructura error.messages
        actor.should(seeThat("el código de respuesta es 422", new ResponseCode(), equalTo(422)));
    }

    @Then("la respuesta debería contener el error de validación")
    public void la_respuesta_debería_contener_el_error_de_validación() {
        actor.should(seeThat("el tipo de error",
            ResponseField.withPath("error.type"),
            equalTo("INPUT_VALIDATION_ERROR")));
    }

    @Then("la respuesta debería contener el error de monto mínimo")
    public void la_respuesta_debería_contener_el_error_de_monto_mínimo() {
        actor.should(seeThat("error de monto mínimo",
            ResponseField.withPath("error.messages"),
            notNullValue()));
    }

    @Then("la respuesta debería contener el error de formato de email")
    public void la_respuesta_debería_contener_el_error_de_formato_de_email() {
        actor.should(seeThat("error de formato de email",
            ResponseField.withPath("error.messages"),
            notNullValue()));
    }

    @Then("la respuesta debería contener el error de documento")
    public void la_respuesta_debería_contener_el_error_de_documento() {
        actor.should(seeThat("error de documento",
            ResponseField.withPath("error.messages"),
            notNullValue()));
    }

    @Then("la respuesta debería contener el error de autenticación")
    public void la_respuesta_debería_contener_el_error_de_autenticación() {
        actor.should(seeThat("código de respuesta", new ResponseCode(), equalTo(401)));
    }

    @Then("la respuesta debería contener el error de moneda inválida")
    public void la_respuesta_debería_contener_el_error_de_moneda_inválida() {
        actor.should(seeThat("error de moneda",
            ResponseField.withPath("error.messages"),
            notNullValue()));
    }

    @Then("la respuesta debería contener el campo {string}")
    public void la_respuesta_debería_contener_el_campo(String fieldName) {
        actor.should(seeThat("el campo " + fieldName,
            ResponseField.withPath(fieldName),
            notNullValue()));
    }

    @Then("el monto en la respuesta debería ser {int}")
    public void el_monto_en_la_respuesta_debería_ser(int expectedAmount) {
        actor.should(seeThat("el monto",
            ResponseField.withPath("amount_in_cents"),
            equalTo(expectedAmount)));
    }


    @Given("que wompi intenta autenticar sin claves de API")
    public void queWompiIntentaAutenticarSinClavesDeAPI() {
        AuthModel model = DatosAuth.llaves();
        actor.attemptsTo(AutenticacionInteraction.datos(model));
    }

    @When("wompi realiza un pago con metodo de pago valido")
    public void wompiRealizaUnPagoConMetodoDePagoValido() {
        actor.attemptsTo(PagoPSETask.datos());
    }

    @When("wompi realiza un pago sin firma de integridad")
    public void wompiRealizaUnPagoSinFirmaDeIntegridad() {
        actor.attemptsTo(PagoPSETask.sinFirma());
    }

    @When("wompi realiza un pago con firma de integridad incorrecta")
    public void wompiRealizaUnPagoConFirmaDeIntegridadIncorrecta() {
        actor.attemptsTo(PagoPSETask.conFirmaInvalida());
    }

    @When("wompi realiza un pago con monto negativo")
    public void wompiRealizaUnPagoConMontoNegativo() {
        actor.attemptsTo(PagoPSETask.conMontoNegativo());
    }

    @When("wompi realiza un pago con monto menor al permitido")
    public void wompiRealizaUnPagoConMontoMenorAlPermitido() {
        actor.attemptsTo(PagoPSETask.conMontoMinimo());
    }

    @When("wompi realiza un pago sin email del cliente")
    public void wompiRealizaUnPagoSinEmailDelCliente() {
        actor.attemptsTo(PagoPSETask.sinEmail());
    }

    @When("wompi realiza un pago con email invalido")
    public void wompiRealizaUnPagoConEmailInvalido() {
        actor.attemptsTo(PagoPSETask.conEmailInvalido());
    }

    @When("wompi realiza un pago sin especificar metodo de pago")
    public void wompiRealizaUnPagoSinEspecificarMetodoDePago() {
        actor.attemptsTo(PagoPSETask.sinMetodoPago());
    }

    @When("wompi realiza un pago sin token de aceptacion")
    public void wompiRealizaUnPagoSinTokenDeAceptacion() {
        actor.attemptsTo(PagoPSETask.sinTokenAceptacion());
    }

    @When("wompi realiza un pago PSE sin codigo de institucion financiera")
    public void wompiRealizaUnPagoPSESinCodigoDeInstitucionFinanciera() {
        actor.attemptsTo(PagoPSETask.sinCodigoBanco());
    }

    @When("wompi realiza un pago PSE con documento de identidad invalido")
    public void wompiRealizaUnPagoPSEConDocumentoDeIdentidadInvalido() {
        actor.attemptsTo(PagoPSETask.conDocumentoInvalido());
    }

    @When("wompi realiza un pago con moneda no soportada")
    public void wompiRealizaUnPagoConMonedaNoSoportada() {
        actor.attemptsTo(PagoPSETask.conMonedaInvalida());
    }

    @When("wompi realiza un pago de {int} centavos")
    public void wompiRealizaUnPagoDeCentavos(int monto) {
        actor.attemptsTo(PagoPSETask.conMonto(monto));
    }

    @Given("que wompi no tiene token de autorizacion")
    public void queWompiNoTieneTokenDeAutorizacion() {
        // No se realiza autenticación, el actor no tiene token
    }

    @Given("existe una transaccion previa con una referencia especifica")
    public void existeUnaTransaccionPreviaConUnaReferenciaEspecifica() {
        // Se realiza un pago con referencia fija para luego duplicarla
        actor.attemptsTo(PagoPSETask.conReferenciaFija());
    }

    @When("wompi intenta realizar un pago con la misma referencia")
    public void wompiIntentaRealizarUnPagoConLaMismaReferencia() {
        // Se intenta realizar otro pago con la misma referencia
        actor.attemptsTo(PagoPSETask.conReferenciaFija());
    }
}
