package automation.wompi.StepDefinitions;

import automation.wompi.Interaction.AutenticacionInteraction;
import automation.wompi.Model.AuthModel;
import automation.wompi.Task.AutenticacionTask;
import automation.wompi.Task.PagoPSETask;
import automation.wompi.Utilities.DatosAuth;
import automation.wompi.questions.ResponseCode;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static automation.wompi.Utilities.Url.UrlBase;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.core.IsEqual.equalTo;

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


    @Given("que wompi intenta autenticar sin claves de API")
    public void queWompiIntentaAutenticarSinClavesDeAPI() {
        AuthModel model = DatosAuth.llaves();
        actor.attemptsTo(AutenticacionInteraction.datos(model));
    }

    @When("wompi realiza un pago con metodo de pago valido")
    public void wompiRealizaUnPagoConMetodoDePagoValido() {
        actor.attemptsTo(PagoPSETask.datos());

    }


}
