package automation.wompi.Task;


import automation.wompi.Interaction.PagoPseInteraction;
import lombok.AllArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

@AllArgsConstructor
public class PagoPSETask implements Task {

    private String tipo;

    public static PagoPSETask datos() {
        return new PagoPSETask("normal");
    }

    public static PagoPSETask sinFirma() {
        return new PagoPSETask("sinFirma");
    }

    public static PagoPSETask conFirmaInvalida() {
        return new PagoPSETask("firmaInvalida");
    }

    public static PagoPSETask conMontoNegativo() {
        return new PagoPSETask("montoNegativo");
    }

    public static PagoPSETask conMontoMinimo() {
        return new PagoPSETask("montoMinimo");
    }

    public static PagoPSETask sinEmail() {
        return new PagoPSETask("sinEmail");
    }

    public static PagoPSETask conEmailInvalido() {
        return new PagoPSETask("emailInvalido");
    }

    public static PagoPSETask sinMetodoPago() {
        return new PagoPSETask("sinMetodoPago");
    }

    public static PagoPSETask sinTokenAceptacion() {
        return new PagoPSETask("sinTokenAceptacion");
    }

    public static PagoPSETask sinCodigoBanco() {
        return new PagoPSETask("sinCodigoBanco");
    }

    public static PagoPSETask conDocumentoInvalido() {
        return new PagoPSETask("documentoInvalido");
    }

    public static PagoPSETask conMonedaInvalida() {
        return new PagoPSETask("monedaInvalida");
    }

    public static PagoPSETask conMonto(int monto) {
        return new PagoPSETask("monto:" + monto);
    }

    public static PagoPSETask conReferenciaFija() {
        return new PagoPSETask("referenciaFija");
    }

    @Override
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(PagoPseInteraction.datos(tipo));
    }
}
