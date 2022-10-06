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

    @GET("asistenciasJustificaciones")
    suspend fun getJustificaciones(): Response<List<JustificacionesSpinnerResponse>>

    @GET("getPlanesSinPago/{id_proveedor}")
    suspend fun getPlanesSinPago(@Path("id_proveedor") id_proveedor: String): Response<List<PlanesSinPagoResponse>>

    @GET("currentRate")
    suspend fun getCurrentRate(): Response<ExchangeUsdRateResponse>

    @GET("currentEurRate")
    suspend fun getCurrentEurRate(): Response<ExchangeEurToUsdRateResponse>

    @GET("currentGbpRate")
    suspend fun getCurrentGbpRate(): Response<ExchangeGbpToUsdRateResponse>

    @GET("check/{locacion}")
    suspend fun getCheckInfoLocacion(@Path("locacion") locacion: String): Response<List<CheckInfoLocacionResponse>>

    @GET("corteDia/{locacion}")
    suspend fun getCorteDia(@Path("locacion") locacion: String): Response<List<CorteResponse>>

    @GET("asistencias")
    suspend fun getAsistencia():Response<List<AsistenciasResponse>>

    @GET("asitenciasComp")
    suspend fun getAsistenciasComp():Response<List<AsistenciasCompResponse>>

    @FormUrlEncoded
    @POST("create/asistencia")
    suspend fun setAsistenia( @Field("id_plan") id_plan: String,
                              @Field("estado") estado:String,
                              @Field("id_justificacion") id_justificacion:String): Response<AsistenciasResponse>

    @FormUrlEncoded
    @POST("create/plan")
    suspend fun setMesaFromApp( @Field("id_locacion") id_locaicon: String,
                                @Field("id_mesa") id_mesa : String,
                                @Field("fecha") fecha: String
    ) : Response<CreatePlanResponse>

    @FormUrlEncoded
    @POST("createPago")
    suspend fun createPago( @Field("id_plan") id_plan: String,
                            @Field("id_concepto") id_concepto: String,
                            @Field("id_locacion") id_locaicon: String,
                            @Field("monto_total") monto_total : String,
                            @Field("pago") pago : String,
                            @Field("fecha_pago") fecha_pago : String
    ) : Response<CreatePagoResponse>

    @FormUrlEncoded
    @POST("createCorte")
    suspend fun createCorte( @Field("id_locacion") id_locacion: String,
                             @Field("usd") usd: String,
                             @Field("pesos") pesos: String,
                             @Field("eur") eur: String,
                             @Field("libras") libras: String,
                             @Field("tipo_cambio_pesos") tipo_cambio_pesos: String,
                             @Field("tipo_cambio_eur") tipo_cambio_eur: String,
                             @Field("tipo_cambio_libras") tipo_cambio_libras: String,
                             @Field("numero_bolsa") numero_bolsa: String,
                             @Field("recibe_bolsa") recibe_bolsa: String,
                             @Field("fecha") fecha: String,
                             @Field("usuario_creacion") usuario_creacion: String
    ) : Response<CorteResponse>

    @FormUrlEncoded
    @POST("createInfoLocacion")
    suspend fun createInfoLocacion(@Field("id_locacion") id_locacion: String,
                                   @Field("ocupacion") ocupacion: String,
                                   @Field("clima") clima: String,
                                   @Field("fecha") fecha: String
    ) : Response<InfoLocacionResponse>
}