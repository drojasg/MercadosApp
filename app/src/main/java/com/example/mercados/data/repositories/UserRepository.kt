package com.example.mercados.data.repositories

import com.example.mercados.data.network.MyApi
import com.example.mercados.data.network.SafeApiRequest
import com.example.mercados.data.network.responses.AuthResponse

class UserRepository: SafeApiRequest() {

    suspend fun userLogin(user: String, password: String) : AuthResponse{

        return apiRequest { MyApi().userLogin(user, password) }

    }

}