package com.example.mercados.data.network.responses

data class CreatePagoResponse(
    var id_pago : String,
    var id_proveedor : String,
    var monto_total : String,
    var pago : String,
    var fecha_pago: String
)
