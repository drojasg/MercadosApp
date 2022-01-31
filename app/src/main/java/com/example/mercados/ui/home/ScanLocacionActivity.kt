package com.example.mercados.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mercados.databinding.ActivityScanLocacionBinding
import com.example.mercados.ui.home.ui.main.AddNewMesaActivity
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class ScanLocacionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityScanLocacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanLocacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScanInicio.setOnClickListener { initScanner() }

    }

    private fun initScanner(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
        integrator.setPrompt("Escanear Ubicacion")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result:IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null){
            if(result.contents == null){
                toast("Sin información")
            }else{
                val intent = Intent(this@ScanLocacionActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val resultado = result.contents
                prefs.saveLocacion("${resultado}")
                intent.putExtra("id_locacion", resultado)
                toast("$resultado")
                startActivity(intent)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}