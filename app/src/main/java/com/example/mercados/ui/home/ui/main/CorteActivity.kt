package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.SpinnerResponse
import com.example.mercados.databinding.ActivityCorteBinding
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CorteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usd = binding.etUsd.text
        val pesos = binding.etPesos.text
        val euros = binding.etEuros.text
        val libras = binding.etLibras.text
        val numeroBolsa = binding.etNumeroBolsa.text
        val recibeBolsa = binding.etRecibeBolsa.text
        val idplan = prefs.getIdPlan()

        binding.btnGuardarCorte.setOnClickListener { setNewCorte(idplan,"$usd", "$pesos",
            "$euros", "$libras", "$numeroBolsa", "$recibeBolsa") }
    }

    private fun setNewCorte(id_plan: String, usd: String, pesos: String,
                            eur: String, libras: String,
                            numeroBolsa: String, recibeBolsa: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).createCorte(id_plan, usd,
            pesos, eur, libras, numeroBolsa, recibeBolsa)
            val body = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    if (body != null){
                        toast("Corte guardado correctamente")
                    }
                }
                else{
                    toast("Ha ocurrido un error")
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://d037-189-174-131-177.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}