package automation.wompi.Utilities;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class SignatureGenerator {

    /**
     * Genera la firma de integridad para transacciones de Wompi
     * Fórmula: SHA256(reference + amount_in_cents + currency + integrity_secret)
     *
     * @param reference Referencia de la transacción
     * @param amountInCents Monto en centavos
     * @param currency Moneda (ej: COP)
     * @param integritySecret Secret de integridad de Wompi
     * @return Firma en formato hexadecimal
     */
    public static String generateSignature(String reference, int amountInCents, String currency, String integritySecret) {
        try {
            // Concatenar los valores según la documentación de Wompi
            String data = reference + amountInCents + currency + integritySecret;

            // Crear instancia de SHA-256
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");

            // Generar el hash
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // Convertir a hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la firma: Algoritmo SHA-256 no disponible", e);
        }
    }

    /**
     * Genera la firma obteniendo automáticamente el integrity secret de las propiedades
     *
     * @param reference Referencia de la transacción
     * @param amountInCents Monto en centavos
     * @param currency Moneda (ej: COP)
     * @return Firma generada
     */
    public static String generateSignature(String reference, int amountInCents, String currency) {
        String integritySecret = DatosAuth.getDatosAuth("auth.integritySecret");
        return generateSignature(reference, amountInCents, currency, integritySecret);
    }
}
