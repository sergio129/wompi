package automation.wompi.Task;


import automation.wompi.Interaction.PagoPseInteraction;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

@AllArgsConstructor
public class PagoPSETask implements Task {

    public static PagoPSETask datos() {
        return new PagoPSETask();
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(PagoPseInteraction.datos());
    }
}
