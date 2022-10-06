package com.example.mercados.ui.home.ui.main

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
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
import java.util.*

class CorteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorteBinding
    private var tipo_cambio_pesos = ""
    private var tipo_cambio_eur = ""
    private var tipo_cambio_libras = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var usercloud = prefs.getUsercloud()

        var usd = binding.etUsd.text
        val pesos = binding.etPesos.text
        val euros = binding.etEuros.text
        val libras = binding.etLibras.text
        val numeroBolsa = binding.etNumeroBolsa.text
        val recibeBolsa = binding.etRecibeBolsa.text
        val idlocacion = prefs.getLocacion()

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val realmonth = month + 1
        val year = c.get(Calendar.YEAR)
        val fecha = "$realmonth-$day-$year"

        getExRate()
        getExEurRate()
        getExGbpRate()

        toast("$usercloud")

        binding.btnGuardarCorte.setOnClickListener {
            if(usd.toString().toInt() < 0 || pesos.toString().toInt() < 0 || euros.toString().toInt() < 0 ||
            libras.toString().toInt() < 0){
            toast("Estas ingresando datos negativos, favor de corregir")
        }else{
                setNewCorte(
                    "$idlocacion", "$usd", "$pesos",
                    "$euros", "$libras", "$tipo_cambio_pesos",
                    "$tipo_cambio_eur", "$tipo_cambio_libras",
                    "$numeroBolsa", "$recibeBolsa", "$fecha", "$usercloud"
                )
        }
        }
    }
    private fun setNewCorte(id_locacion:String, usd: String, pesos: String,
                            eur: String, libras: String, tipo_cambio_pesos: String,
                            tipo_cambio_eur: String, tipo_cambio_libras: String,
                            numeroBolsa: String, recibeBolsa: String, fecha: String, usuario_creacion: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).createCorte(id_locacion, usd,
            pesos, eur, libras, tipo_cambio_pesos, tipo_cambio_eur, tipo_cambio_libras, numeroBolsa, recibeBolsa, fecha, usuario_creacion)
            val body = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    if (body != null){
                        toast("Corte guardado correctamente")
                        super.finish()
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
            .baseUrl("https://smart-hotels-lead-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getExRate(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCurrentRate()
            val exRate = call.body()
            runOnUiThread { 
                if (exRate != null) {
                    tipo_cambio_pesos = exRate.CurrentRate
                }
            }
        }
    }

    fun getExEurRate(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCurrentEurRate()
            val exRate = call.body()
            runOnUiThread {
                if (exRate != null){
                    tipo_cambio_eur = exRate.CurrentRate
                }
            }
        }
    }

    fun getExGbpRate(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCurrentGbpRate()
            val exRate = call.body()
            runOnUiThread {
                if (exRate != null){
                    tipo_cambio_libras = exRate.CurrentRate
                }
            }
        }
    }

}