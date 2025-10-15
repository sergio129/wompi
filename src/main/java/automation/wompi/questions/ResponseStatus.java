package automation.wompi.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ResponseStatus implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor)
                .path("data.status");
    }

    public static ResponseStatus value() {
        return new ResponseStatus();
    }
}

