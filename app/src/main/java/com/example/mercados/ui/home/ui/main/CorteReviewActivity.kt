package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.databinding.ActivityCorteReviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CorteReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCorteReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val locacion = Mercados.prefs.getLocacion()
        getCorte("$locacion")
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://shiny-roses-call-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getCorte(idlocacion: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCorteDia("$idlocacion")
            val corte = call.body()
            runOnUiThread{
                if (corte != null){
                    binding.tvtipocambiopesos.text = corte[0].tipo_cambio_pesos
                    binding.tvtipocambioeur.text = corte[0].tipo_cambio_eur
                    binding.tvtipocambiolib.text = corte[0].tipo_cambio_libras
                    binding.tvdlrs.text = corte[0].usd
                    binding.tveur.text = corte[0].eur
                    binding.tvlib.text = corte[0].libras
                    binding.tvpesos.text = corte[0].pesos
                }
            }
        }
    }
}