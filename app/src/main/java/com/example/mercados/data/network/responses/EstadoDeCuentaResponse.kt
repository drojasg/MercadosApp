package com.example.mercados.data.network.responses

data class EstadoDeCuentaResponse(
    var id_plan : String,
    var artesano : String,
    var giro : String,
    var locacion : String,
    var monto : Float,
    var monto_pago : Float,
    var pendiente : Float,
    var fecha : String
)
