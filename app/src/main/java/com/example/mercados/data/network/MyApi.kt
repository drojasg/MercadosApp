package com.example.mercados.data.network

import com.example.mercados.data.network.responses.AuthResponse
import com.example.mercados.data.network.responses.LocacionResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("controlaccess")
    suspend fun userLogin(
        @Field("username") user: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    companion object{
        operator fun invoke() : MyApi{
            return Retrofit.Builder()
                .baseUrl("http://e1b0-189-174-127-179.ngrok.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}