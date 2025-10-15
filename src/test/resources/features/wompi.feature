Feature: Autenticación de API de Pagos
  Como comerciante
  Quiero autenticarme con la API de Wompi
  Para poder realizar transacciones seguras

  @AutExitosa
  Scenario: Autenticación exitosa de la API
    Given que wompi tiene claves de API válidas
    When wompi hace una solicitud para autenticar
    Then el estado de la respuesta debería ser 200

  @AutCla
  Scenario: Autenticación fallida sin clave de API
    Given que wompi intenta autenticar sin claves de API
    When wompi hace una solicitud para autenticar
    Then el estado de la respuesta debería ser 402

  @PagoExitoso
  Scenario: Pago exitoso con PSE
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con metodo de pago valido
    Then el estado de la respuesta debería ser 201
    And la respuesta debería contener el estado "PENDING"
    And la respuesta debería contener el tipo de pago "PSE"

  # ESCENARIOS ALTERNOS - CASOS DE ERROR

  @PagoSinFirma
  Scenario: Intento de pago sin firma de integridad
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago sin firma de integridad
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "La firma es requerida"

  @PagoFirmaInvalida
  Scenario: Intento de pago con firma inválida
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con firma de integridad incorrecta
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "La firma es inválida"

  @PagoMontoInvalido
  Scenario: Intento de pago con monto negativo
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con monto negativo
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error de validación

  @PagoMontoMinimo
  Scenario: Intento de pago por debajo del monto mínimo
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con monto menor al permitido
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error de monto mínimo

  @PagoSinEmail
  Scenario: Intento de pago sin email del cliente
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago sin email del cliente
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "customer_email es requerido"

  @PagoEmailInvalido
  Scenario: Intento de pago con email inválido
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con email invalido
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error de formato de email

  @PagoSinMetodoPago
  Scenario: Intento de pago sin método de pago
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago sin especificar metodo de pago
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "payment_method es requerido"

  @PagoSinTokenAceptacion
  Scenario: Intento de pago sin token de aceptación
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago sin token de aceptacion
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "acceptance_token es requerido"

  @PagoPSESinBanco
  Scenario: Intento de pago PSE sin especificar banco
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago PSE sin codigo de institucion financiera
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "financial_institution_code es requerido"

  @PagoPSEDocumentoInvalido
  Scenario: Intento de pago PSE con documento inválido
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago PSE con documento de identidad invalido
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error de documento

  @PagoSinAutorizacion
  Scenario: Intento de pago sin token de autorización
    Given que wompi no tiene token de autorizacion
    When wompi realiza un pago con metodo de pago valido
    Then el estado de la respuesta debería ser 401
    And la respuesta debería contener el error de autenticación

  @PagoMonedaInvalida
  Scenario: Intento de pago con moneda no soportada
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con moneda no soportada
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error de moneda inválida

  # ESCENARIOS DE VALIDACIÓN DE RESPUESTA

  @ValidarEstructuraRespuesta
  Scenario: Validar estructura de respuesta exitosa
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con metodo de pago valido
    Then el estado de la respuesta debería ser 201
    And la respuesta debería contener el campo "id"
    And la respuesta debería contener el campo "reference"
    And la respuesta debería contener el campo "created_at"
    And la respuesta debería contener el campo "amount_in_cents"
    And la respuesta debería contener el campo "status"

  @ValidarMontos
  Scenario: Validar que el monto en la respuesta coincida con el enviado
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago de 4950000 centavos
    Then el estado de la respuesta debería ser 201
    And el monto en la respuesta debería ser 4950000

  @PagoDuplicado
  Scenario: Intento de pago con referencia duplicada
    Given que wompi tiene claves de API válidas
    And existe una transaccion previa con una referencia especifica
    When wompi intenta realizar un pago con la misma referencia
    Then el estado de la respuesta debería ser 422
    And la respuesta debería contener el error "La referencia ya ha sido usada"
