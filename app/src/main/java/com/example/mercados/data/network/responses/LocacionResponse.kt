package com.example.mercados.data.network.responses

data class LocacionResponse(
    var id_plan: String,
    var id_mesa: String,
    var id_proveedor: String,
    var artesano: String,
    var giro: String,
    var fecha: String,
    var locacion: String,
    var monto: String,
    var asistencia: String,
    var falta: String
)