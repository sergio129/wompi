package automation.wompi.Task;

import automation.wompi.Interaction.AutenticacionInteraction;
import automation.wompi.Model.AuthModel;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

@AllArgsConstructor
public class AutenticacionTask implements Task {
    private final AuthModel aut;

    public static AutenticacionTask datos(AuthModel aut) {
        return new AutenticacionTask(aut);
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(AutenticacionInteraction.datos(aut));
    }
}
