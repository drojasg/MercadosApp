package com.example.mercados.data.network.responses

data class CorteResponse(
    var id_corte : String,
    var id_plan : String,
    var usd : String,
    var pesos : String,
    var eur : String,
    var libras : String,
    var numero_bolsa : String,
    var recibe_bolsa : String,
)
