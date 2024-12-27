package automation.wompi.Interaction;

import automation.wompi.Model.AuthModel;
import lombok.AllArgsConstructor;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;

@AllArgsConstructor
public class AutenticacionInteraction implements Interaction {
    private static final String MERCHANT_ENDPOINT = "/merchants/";
    private final AuthModel aut;

    public static AutenticacionInteraction datos(AuthModel aut) {
        return new AutenticacionInteraction(aut);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(MERCHANT_ENDPOINT + aut.getPublicKey())

        );


        Serenity.setSessionVariable("data.presigned_acceptance.acceptance_token").to(acceptance());
        Serenity.setSessionVariable("data.presigned_personal_data_auth.acceptance_token").to(personal());

    }

    public static String acceptance() {
        String tokenSession = (SerenityRest.lastResponse().body().jsonPath().getString("data.presigned_acceptance.acceptance_token"));
        return tokenSession;
    }

    public static String personal() {
        String session = (SerenityRest.lastResponse().body().jsonPath().getString("data.presigned_personal_data_auth.acceptance_token"));
        return session;
    }

    public static String TokenAcceptance() {
        return Serenity.sessionVariableCalled("data.presigned_acceptance.acceptance_token");
    }

    public static String TokenPersonal() {
        return Serenity.sessionVariableCalled("data.presigned_personal_data_auth.acceptance_token");
    }
}
