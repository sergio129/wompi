Feature:Autenticación de API de Pagos
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

  @PagoR
  Scenario: Pago
    Given que wompi tiene claves de API válidas
    When wompi realiza un pago con metodo de pago valido
    Then el estado de la respuesta debería ser 200