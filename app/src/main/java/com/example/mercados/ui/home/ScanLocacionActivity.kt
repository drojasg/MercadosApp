package com.example.mercados.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.CheckInfoLocacionResponse
import com.example.mercados.databinding.ActivityScanLocacionBinding
import com.example.mercados.ui.home.ui.main.InfoLocacionActivity
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ScanLocacionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanLocacionBinding
    var capturado = ""
    private val infoList = mutableListOf<CheckInfoLocacionResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanLocacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanInicio.setOnClickListener { initScanner() }

    }

    private fun initScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear Ubicacion")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://shiny-roses-call-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun checkInfoLocacion(locacion: String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCheckInfoLocacion("$locacion")
            val info = call.body()
            if (call.isSuccessful){
                val lista = info ?: emptyList()
                infoList.clear()
                infoList.addAll(lista)
                for (info in infoList) {
                    if (info.existe == "1"){
                        capturado = "1"
                    }else{
                        capturado = "0"
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null){
            if(result.contents == null){
                toast("{Sin información")
            }else{
                val locacion = result.contents
                checkInfoLocacion("$locacion")
                if(capturado == "1"){
                    prefs.saveLocacion(locacion)
                    val intent = Intent(this@ScanLocacionActivity, MainActivity::class.java)
                    startActivity(intent)
                    toast("$capturado")
                    Log.d("AQUI", "$capturado")
                }else if (capturado == "0"){
                    val intent = Intent(this@ScanLocacionActivity, InfoLocacionActivity::class.java)
                    val resultado = result.contents
                    prefs.saveLocacion("${resultado}")
                    intent.putExtra("id_locacion", resultado)
                    startActivity(intent)
                    toast("$capturado")
                    Log.d("O AQUIIII", "$capturado")
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}