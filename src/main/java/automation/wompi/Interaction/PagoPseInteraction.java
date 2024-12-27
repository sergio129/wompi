package automation.wompi.Interaction;

import automation.wompi.Builders.PseBuild;
import automation.wompi.Utilities.Post;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;


public class PagoPseInteraction implements Interaction {

    public static PagoPseInteraction datos() {
        return new PagoPseInteraction();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(("/transactions"))
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Authorization", " Bearer prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg")
                                .body(PseBuild.Principal())
                        )
        );

    }
}
