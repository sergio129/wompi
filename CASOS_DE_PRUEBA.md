# Diseño de Casos de Prueba - API Wompi PSE

## Información del Proyecto
- **Método de Pago**: PSE (Pago Seguro en Línea)
- **Patrón de Diseño**: Screenplay Pattern con Serenity BDD
- **Lenguaje**: Java 11 LTS
- **Framework BDD**: Cucumber + Gherkin
- **API Base**: https://api-sandbox.co.uat.wompi.dev/v1

---

## 1. Escenarios de Prueba Exitosos (Happy Path)

### CP-001: Autenticación Exitosa de la API
**Objetivo**: Validar que el comercio puede autenticarse correctamente con las claves API proporcionadas.

**Precondiciones**:
- Claves API válidas (pub_stagtest y prv_stagtest)

**Pasos**:
1. Given: Tener claves de API válidas
2. When: Realizar petición GET a /merchants/{public_key}
3. Then: Recibir código de respuesta 200

**Resultado Esperado**: 
- Status Code: 200 OK
- Response contiene información del comercio

**Prioridad**: Alta

---

### CP-002: Pago Exitoso con PSE
**Objetivo**: Crear una transacción exitosa usando el método de pago PSE.

**Precondiciones**:
- Autenticación exitosa
- Datos de prueba válidos de PSE
- Firma de integridad correcta

**Pasos**:
1. Given: Claves API válidas
2. When: Enviar POST a /transactions con:
   - Referencia única generada automáticamente
   - Monto: 4,950,000 COP (49,500 pesos)
   - Método de pago: PSE
   - Datos del cliente válidos
   - Firma SHA-256 calculada
   - Tokens de aceptación válidos
3. Then: Recibir código 201 Created

**Datos de Entrada**:
```json
{
  "amount_in_cents": 4950000,
  "currency": "COP",
  "payment_method_type": "PSE",
  "customer_email": "sanayaromero62@gmail.com",
  "payment_method": {
    "type": "PSE",
    "user_type": "0",
    "user_legal_id_type": "CC",
    "user_legal_id": "1015411162",
    "financial_institution_code": "1"
  }
}
```

**Resultado Esperado**:
- Status Code: 201 Created
- Response contiene:
  - `id`: ID único de transacción
  - `status`: "PENDING"
  - `payment_method_type`: "PSE"
  - `reference`: Referencia enviada
  - `amount_in_cents`: 4950000

**Prioridad**: Crítica

---

### CP-003: Validación de Estructura de Respuesta
**Objetivo**: Verificar que la respuesta de una transacción exitosa contiene todos los campos requeridos.

**Campos Obligatorios a Validar**:
- ✅ `data.id`
- ✅ `data.reference`
- ✅ `data.created_at`
- ✅ `data.amount_in_cents`
- ✅ `data.status`
- ✅ `data.currency`
- ✅ `data.payment_method_type`
- ✅ `data.customer_email`

**Prioridad**: Alta

---

## 2. Escenarios Alternos - Validación de Firma

### CP-004: Pago sin Firma de Integridad
**Objetivo**: Validar que la API rechace transacciones sin firma.

**Pasos**:
1. Given: Claves API válidas
2. When: Enviar transacción con `signature: null`
3. Then: Recibir error 422

**Resultado Esperado**:
```json
{
  "error": {
    "type": "INPUT_VALIDATION_ERROR",
    "messages": {
      "signature": ["La firma es requerida"]
    }
  }
}
```

**Prioridad**: Alta

---

### CP-005: Pago con Firma Inválida
**Objetivo**: Validar que la API rechace firmas incorrectas.

**Pasos**:
1. Given: Claves API válidas
2. When: Enviar transacción con firma incorrecta
3. Then: Recibir error 422

**Resultado Esperado**:
```json
{
  "error": {
    "type": "INPUT_VALIDATION_ERROR",
    "messages": {
      "signature": ["La firma es inválida"]
    }
  }
}
```

**Prioridad**: Alta

---

## 3. Escenarios Alternos - Validación de Montos

### CP-006: Pago con Monto Negativo
**Objetivo**: Validar que la API rechace montos negativos.

**Datos de Entrada**:
```json
{
  "amount_in_cents": -1000
}
```

**Resultado Esperado**:
- Status Code: 422
- Error de validación de monto

**Prioridad**: Media

---

### CP-007: Pago por Debajo del Monto Mínimo
**Objetivo**: Validar que la API rechace montos menores al permitido.

**Datos de Entrada**:
```json
{
  "amount_in_cents": 100
}
```

**Resultado Esperado**:
- Status Code: 422
- Error: "El monto es menor al permitido"

**Prioridad**: Media

---

## 4. Escenarios Alternos - Validación de Datos del Cliente

### CP-008: Pago sin Email del Cliente
**Objetivo**: Validar que customer_email es obligatorio.

**Datos de Entrada**:
```json
{
  "customer_email": null
}
```

**Resultado Esperado**:
- Status Code: 422
- Error: "customer_email es requerido"

**Prioridad**: Alta

---

### CP-009: Pago con Email Inválido
**Objetivo**: Validar formato de email.

**Datos de Entrada**:
```json
{
  "customer_email": "correo-invalido"
}
```

**Resultado Esperado**:
- Status Code: 422
- Error de formato de email

**Prioridad**: Media

---

## 5. Escenarios Alternos - Validación de Método de Pago PSE

### CP-010: PSE sin Código de Institución Financiera
**Objetivo**: Validar que el banco es obligatorio para PSE.

**Datos de Entrada**:
```json
{
  "payment_method": {
    "type": "PSE",
    "financial_institution_code": null
  }
}
```

**Resultado Esperado**:
- Status Code: 422
- Error: "financial_institution_code es requerido"

**Prioridad**: Alta

---

### CP-011: PSE con Documento de Identidad Inválido
**Objetivo**: Validar formato de documento.

**Datos de Entrada**:
```json
{
  "payment_method": {
    "user_legal_id": "ABC123"
  }
}
```

**Resultado Esperado**:
- Status Code: 422
- Error de documento inválido

**Prioridad**: Media

---

## 6. Escenarios Alternos - Validación de Autenticación

### CP-012: Pago sin Token de Autorización
**Objetivo**: Validar que la autenticación es obligatoria.

**Pasos**:
1. Given: Sin token de autorización
2. When: Enviar POST a /transactions
3. Then: Recibir error 401

**Resultado Esperado**:
- Status Code: 401 Unauthorized
- Error de autenticación

**Prioridad**: Crítica

---

## 7. Escenarios Alternos - Validación de Tokens

### CP-013: Pago sin Token de Aceptación
**Objetivo**: Validar que acceptance_token es obligatorio.

**Datos de Entrada**:
```json
{
  "acceptance_token": null
}
```

**Resultado Esperado**:
- Status Code: 422
- Error: "acceptance_token es requerido"

**Prioridad**: Alta

---

## 8. Escenarios Alternos - Validación de Unicidad

### CP-014: Pago con Referencia Duplicada
**Objetivo**: Validar que las referencias deben ser únicas.

**Pasos**:
1. Given: Existe una transacción previa con referencia "REF123"
2. When: Intentar crear otra transacción con "REF123"
3. Then: Recibir error 422

**Resultado Esperado**:
```json
{
  "error": {
    "type": "INPUT_VALIDATION_ERROR",
    "messages": {
      "reference": ["La referencia ya ha sido usada"]
    }
  }
}
```

**Prioridad**: Alta

---

## 9. Escenarios Alternos - Validación de Moneda

### CP-015: Pago con Moneda No Soportada
**Objetivo**: Validar que solo se aceptan monedas soportadas.

**Datos de Entrada**:
```json
{
  "currency": "USD"
}
```

**Resultado Esperado**:
- Status Code: 422
- Error: "Moneda no soportada"

**Prioridad**: Baja

---

## Matriz de Cobertura de Pruebas

| Categoría | Total Casos | Críticos | Altos | Medios | Bajos |
|-----------|-------------|----------|-------|--------|-------|
| Happy Path | 3 | 1 | 2 | 0 | 0 |
| Validación Firma | 2 | 0 | 2 | 0 | 0 |
| Validación Montos | 2 | 0 | 0 | 2 | 0 |
| Validación Cliente | 2 | 0 | 1 | 1 | 0 |
| Validación PSE | 2 | 0 | 1 | 1 | 0 |
| Validación Auth | 1 | 1 | 0 | 0 | 0 |
| Validación Tokens | 1 | 0 | 1 | 0 | 0 |
| Validación Unicidad | 1 | 0 | 1 | 0 | 0 |
| Validación Moneda | 1 | 0 | 0 | 0 | 1 |
| **TOTAL** | **17** | **2** | **8** | **4** | **1** |

---

## Datos de Prueba

### Claves API (Sandbox)
```
Public Key: pub_stagtest_g2u0HQd3ZMh05hsSgTS2lUV8t3s4mOt7
Private Key: prv_stagtest_5i0ZGIGiFcDQifYsXxvsny7Y37tKqFWg
Integrity Secret: stagtest_integrity_nAIBuqayW70XpUqJS4qf4STYiISd89Fp
```

### Datos de Cliente de Prueba
```
Nombre: Sergio Anaya Romero
Email: sanayaromero62@gmail.com
Teléfono: +573103904286
Tipo Documento: CC (Cédula de Ciudadanía)
Documento: 1015411162
```

### Códigos de Institución Financiera PSE
```
1 - Banco de Prueba
```

---

## Fórmula de Firma de Integridad

```java
SHA256(reference + amount_in_cents + currency + integrity_secret)
```

**Ejemplo**:
```
Entrada: "TEST_20251015081525_A7077P" + "4950000" + "COP" + "stagtest_integrity_..."
Salida: "823416f7ea985acd7a787ad4bd6b8d8ee5b531fab4dcff73d3f66c92368b509f"
```

---

## Automatización Implementada

### Tecnologías
- **Java 11 LTS**
- **Serenity BDD 4.1.20**
- **Cucumber (Gherkin)**
- **Rest Assured 5.4.0**
- **Lombok 1.18.34**

### Patrón de Diseño
**Screenplay Pattern** con las siguientes capas:
- **Tasks**: `PagoPSETask`, `AutenticacionTask`
- **Interactions**: `PagoPseInteraction`, `AutenticacionInteraction`
- **Questions**: `ResponseCode`
- **Models**: `PseModel`, `CustomerdataModel`, `PaymentMethodModel`
- **Builders**: `PseBuild`
- **Utilities**: `SignatureGenerator`, `ReferenceGenerator`

### Características Implementadas
✅ Generación automática de firma SHA-256
✅ Generación automática de referencias únicas
✅ Validación de estructura de respuesta
✅ Manejo de errores HTTP
✅ Reportes HTML con Serenity

---

## Ejecución de Pruebas

### Comando para ejecutar todas las pruebas
```bash
.\gradlew test
```

### Comando para ejecutar solo pruebas de pago exitoso
```bash
.\gradlew test --tests "*PagoExitoso*"
```

### Comando para ejecutar pruebas alternativas
```bash
.\gradlew test --tests "*PagoSinFirma*"
```

### Generar reportes
```bash
.\gradlew test aggregate
```

Los reportes se generan en: `target/site/serenity/index.html`

---

## Próximos Pasos

1. ✅ Implementar step definitions para escenarios alternos
2. ✅ Crear builders para casos de error
3. ✅ Agregar validaciones de campos de respuesta
4. ✅ Implementar data-driven testing con diferentes montos
5. ✅ Agregar pruebas de rendimiento (opcional)
6. ✅ Integración con CI/CD (opcional)

