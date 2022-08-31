package com.example.mercados.data.network.responses

data class CorteResponse(
    var id_corte : String,
    var id_plan : String,
    var usd : String,
    var pesos : String,
    var eur : String,
    var libras : String,
    var tipo_cambio_pesos : String,
    var tipo_cambio_eur : String,
    var tipo_cambio_libras : String,
    var numero_bolsa : String,
    var recibe_bolsa : String,
    var fecha : String
)
