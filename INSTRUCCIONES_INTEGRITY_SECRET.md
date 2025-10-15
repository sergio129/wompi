# Cómo obtener el Integrity Secret de Wompi

## Problema Actual
La prueba está fallando porque la firma (`signature`) es rechazada por Wompi con el error:
```
"La firma es inválida"
```

## Causa
El `integrity_secret` en el archivo `Auth.properties` es un valor de ejemplo. Necesitas el valor real de tu cuenta de Wompi.

## Solución: Obtener el Integrity Secret Real

### Opción 1: Desde el Dashboard de Wompi
1. Accede a https://comercios.wompi.co (o el dashboard de staging)
2. Inicia sesión con tu cuenta
3. Ve a **Configuración** → **Llaves de API** o **API Keys**
4. Busca el campo llamado **"Integrity Secret"** o **"Event Secret"**
5. Copia el valor completo (debería empezar con `stagtest_integrity_` para staging)

### Opción 2: Contactar Soporte de Wompi
Si no encuentras el integrity secret en el dashboard:
1. Contacta al soporte de Wompi
2. Solicita el "Integrity Secret" o "Event Secret" para el ambiente staging/sandbox

## Cómo Actualizar el Proyecto

Una vez que tengas el integrity secret correcto, actualiza el archivo:

**Archivo:** `src/test/resources/Auth.properties`

```properties
auth.publicKey=pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7
auth.privateKey=prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg
auth.integritySecret=TU_INTEGRITY_SECRET_AQUI
```

## Verificar que Funciona

Después de actualizar el integrity secret, ejecuta las pruebas:

```bash
.\gradlew test --tests "*wompi*"
```

Si el integrity secret es correcto, verás un código de respuesta **200** o **201** en lugar del **422**.

## Fórmula de la Firma

La firma se genera usando SHA-256 con la siguiente fórmula:

```
SHA256(reference + amount_in_cents + currency + integrity_secret)
```

Ejemplo con tus datos actuales:
- reference: `DARQT1BJY9FQ`
- amount_in_cents: `4950000`
- currency: `COP`
- integrity_secret: `[TU_SECRET_REAL]`

String a hashear: `DARQT1BJY9FQ4950000COP[TU_SECRET_REAL]`

## Nota Importante

⚠️ **NUNCA** compartas tu integrity secret públicamente o lo subas a repositorios públicos de GitHub.
Es tan sensible como una contraseña.

