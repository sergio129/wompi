package automation.wompi.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class ResponsePaymentMethodType implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return LastResponse.received().answeredBy(actor)
                .path("data.payment_method_type");
    }

    public static ResponsePaymentMethodType value() {
        return new ResponsePaymentMethodType();
    }
}

