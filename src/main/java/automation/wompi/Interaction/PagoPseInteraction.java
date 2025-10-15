package automation.wompi.Interaction;

import automation.wompi.Builders.PseBuild;
import automation.wompi.Utilities.Post;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PagoPseInteraction implements Interaction {

    private String tipo;

    public static PagoPseInteraction datos(String tipo) {
        return new PagoPseInteraction(tipo);
    }

    public static PagoPseInteraction datos() {
        return new PagoPseInteraction("normal");
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Object body;

        switch (tipo) {
            case "sinFirma":
                body = PseBuild.sinFirma();
                break;
            case "firmaInvalida":
                body = PseBuild.conFirmaInvalida();
                break;
            case "montoNegativo":
                body = PseBuild.conMontoNegativo();
                break;
            case "montoMinimo":
                body = PseBuild.conMontoMinimo();
                break;
            case "sinEmail":
                body = PseBuild.sinEmail();
                break;
            case "emailInvalido":
                body = PseBuild.conEmailInvalido();
                break;
            case "sinMetodoPago":
                body = PseBuild.sinMetodoPago();
                break;
            case "sinTokenAceptacion":
                body = PseBuild.sinTokenAceptacion();
                break;
            case "sinCodigoBanco":
                body = PseBuild.sinCodigoBanco();
                break;
            case "documentoInvalido":
                body = PseBuild.conDocumentoInvalido();
                break;
            case "monedaInvalida":
                body = PseBuild.conMonedaInvalida();
                break;
            case "referenciaFija":
                body = PseBuild.conReferenciaFija();
                break;
            default:
                if (tipo.startsWith("monto:")) {
                    int monto = Integer.parseInt(tipo.substring(6));
                    body = PseBuild.conMonto(monto);
                } else {
                    body = PseBuild.Principal();
                }
                break;
        }

        actor.attemptsTo(
                Post.to(("/transactions"))
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .header("Authorization", " Bearer prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg")
                                .body(body)
                        )
        );
    }
}
