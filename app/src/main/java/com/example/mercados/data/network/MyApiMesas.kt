package com.example.mercados.data.network

import com.example.mercados.data.network.responses.*
import retrofit2.Response
import retrofit2.http.*

interface MyApiMesas {
    @GET("getMesasAtLocacion/{id_locacion}")
    suspend fun getMesas(@Path("id_locacion") id_locaicon:String): Response<List<LocacionResponse>>

    @GET("getMesaSpinner/{id_locacion}")
    suspend fun getMesaSpinner(@Path("id_locacion") id_locaicon: String): Response<List<SpinnerResponse>>

    @GET("getEstadoCuenta/{id_proveedor}")
    suspend fun  getEstadoCuenta(@Path("id_proveedor") id_proveedor: String): Response<List<EstadoDeCuentaResponse>>

    @GET("currentRate")
    suspend fun getCurrentRate(): Response<ExchangeUsdRateResponse>

    @GET("currentEurRate")
    suspend fun getCurrentEurRate(): Response<ExchangeEurToUsdRateResponse>

    @GET("currentGbpRate")
    suspend fun getCurrentGbpRate(): Response<ExchangeGbpToUsdRateResponse>

    @FormUrlEncoded
    @POST("create/asistencia")
    suspend fun setAsistenia( @Field("id_plan") id_plan: String): Response<AsistenciasResponse>

    @FormUrlEncoded
    @POST("create/plan")
    suspend fun setMesaFromApp( @Field("id_locacion") id_locaicon: String,
                                @Field("id_mesa") id_mesa : String,
                                @Field("fecha") fecha: String) : Response<CreatePlanResponse>
}