package automation.wompi.Utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ReferenceGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int REFERENCE_LENGTH = 12;

    /**
     * Genera una referencia única para la transacción
     * Formato: REF_TIMESTAMP_RANDOM
     *
     * @return Referencia única
     */
    public static String generateUniqueReference() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = generateRandomString(6);
        return "TEST_" + timestamp + "_" + randomPart;
    }

    /**
     * Genera una referencia aleatoria simple
     *
     * @return Referencia aleatoria de 12 caracteres
     */
    public static String generateSimpleReference() {
        return generateRandomString(REFERENCE_LENGTH);
    }

    /**
     * Genera un string aleatorio de longitud especificada
     *
     * @param length Longitud del string
     * @return String aleatorio
     */
    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}

