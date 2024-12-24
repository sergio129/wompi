package automation.wompi.Interaction;

import automation.wompi.Model.AuthModel;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;

@AllArgsConstructor
public class authInteraction implements Interaction {
    private static final String MERCHANT_ENDPOINT = "/merchants/";
    private final AuthModel aut;

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(MERCHANT_ENDPOINT + aut.getPublicKey())

        );

    }

    public static authInteraction datos(AuthModel aut) {
        return new authInteraction(aut);
    }
}
