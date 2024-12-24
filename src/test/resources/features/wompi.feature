Feature:Autenticación de API de Pagos
  Como comerciante
  Quiero autenticarme con la API de Wompi
  Para poder realizar transacciones seguras

  @AutExitosa
  Scenario: Autenticación exitosa de la API
    Given que John tiene claves de API válidas
    When John hace una solicitud para autenticar
    Then el estado de la respuesta debería ser 200
