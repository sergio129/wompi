package automation.wompi.StepDefinitions;

import automation.wompi.Interaction.authInteraction;
import automation.wompi.Model.AuthModel;
import automation.wompi.Utilities.DatosAuth;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static automation.wompi.Utilities.Url.UrlBase;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class wompiSteps {
    Actor actor = Actor.named("Wompi");

    @Before
    public void setUp() {
        actor.whoCan(CallAnApi.at(UrlBase));
    }

    @Given("que John tiene claves de API válidas")
    public void queJohnTieneClavesDeAPIVálidas() {
        AuthModel model = DatosAuth.llaves();
        actor.attemptsTo(authInteraction.datos(model));
    }
    @When("John hace una solicitud para autenticar")
    public void johnHaceUnaSolicitudParaAutenticar() {
        // Solicitud ya realizada en el paso @Dado
    }

    @Then("el estado de la respuesta debería ser {int}")
    public void el_estado_de_la_respuesta_debería_ser(int statusCode) {
        actor.should(seeThatResponse("Código de respuesta esperado",
                response -> response.statusCode(statusCode)));
    }


}
