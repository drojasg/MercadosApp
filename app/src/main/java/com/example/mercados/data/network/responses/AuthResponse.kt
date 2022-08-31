package com.example.mercados.data.network.responses

data class AuthResponse(
    val isSuccsessfull : Boolean?,
    val status: String?,
    val error: String?,
    val usercloud: String?
)
