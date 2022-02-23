package com.example.mercados.data.network

import com.example.mercados.data.network.responses.*
import retrofit2.Response
import retrofit2.http.*

interface MyApiMesas {
    @GET("getMesasAtLocacion/{id_locacion}")
    suspend fun getMesas(@Path("id_locacion") id_locaicon:String): Response<List<LocacionResponse>>

    @GET("getMesaSpinner/{id_locacion}")
    suspend fun getMesaSpinner(@Path("id_locacion") id_locaicon: String): Response<List<SpinnerResponse>>

    @GET("conceptos")
    suspend fun getConceptsoSpinner(): Response<List<ConceptosSpinnerResponse>>

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
    suspend fun setAsistenia( @Field("id_plan") id_plan: String,
                              @Field("estado") estado:String): Response<AsistenciasResponse>

    @FormUrlEncoded
    @POST("create/plan")
    suspend fun setMesaFromApp( @Field("id_locacion") id_locaicon: String,
                                @Field("id_mesa") id_mesa : String,
                                @Field("fecha") fecha: String
    ) : Response<CreatePlanResponse>

    @FormUrlEncoded
    @POST("createPago")
    suspend fun createPago( @Field("id_proveedor") id_proveedor: String,
                            @Field("id_concepto") id_concepto: String,
                            @Field("monto_total") monto_total : String,
                            @Field("pago") pago : String,
                            @Field("fecha_pago") fecha_pago : String
    ) : Response<CreatePagoResponse>

    @FormUrlEncoded
    @POST("createCorte")
    suspend fun createCorte( @Field("id_plan") id_plan: String,
                             @Field("usd") usd: String,
                             @Field("pesos") pesos: String,
                             @Field("eur") eur: String,
                             @Field("libras") libras: String,
                             @Field("numero_bolsa") numero_bolsa: String,
                             @Field("recibe_bolsa") recibe_bolsa: String
    ) : Response<CorteResponse>
}