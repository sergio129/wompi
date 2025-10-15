package automation.wompi.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ResponseField implements Question<Object> {

    private final String fieldPath;

    public ResponseField(String fieldPath) {
        this.fieldPath = fieldPath;
    }

    @Override
    public Object answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor)
                .path("data." + fieldPath);
    }

    public static ResponseField withPath(String fieldPath) {
        return new ResponseField(fieldPath);
    }
}

