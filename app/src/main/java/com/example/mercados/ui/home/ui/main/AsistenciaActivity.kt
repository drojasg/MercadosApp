package com.example.mercados.ui.home.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.databinding.ActivityAsistenciaBinding
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AsistenciaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAsistenciaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAsistenciaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle = intent.extras
        val idplan = bundle?.get("idplan")
        val idmesa = bundle?.get("idmesa")
        val idproveedor = bundle?.get("idproveedor")
        val artesano = bundle?.get("artesano")
        val giro = bundle?.get("giro")
        val fecha = bundle?.get("fecha")
        val locacion = bundle?.get("locacion")
        val renta = bundle?.get("renta")

        binding.idplan.text = (idplan as CharSequence?)!!
        binding.idmesa.text = (idmesa as CharSequence?)!!
        binding.idproveedor.text = (idproveedor as CharSequence?)!!
        binding.artesano.text = (artesano as CharSequence?)!!
        binding.giro.text = (giro as CharSequence?)!!
        binding.fecha.text = (fecha as CharSequence?)!!
        binding.locacion.text = (locacion as CharSequence?)!!
        binding.renta.text = renta as CharSequence?

        val toPass = Bundle()
        toPass.putString("idplan", idplan.toString())
        toPass.putString("idmesa", idmesa.toString())
        toPass.putString("idproveedor", idproveedor.toString())
        toPass.putString("artesano", artesano.toString())
        toPass.putString("giro", giro.toString())
        toPass.putString("fecha", fecha.toString())
        toPass.putString("locacion", locacion.toString())
        toPass.putString("renta", renta.toString())

        val intent = Intent(this, SaldosActivity::class.java)
        intent.putExtras(toPass)

        binding.btnAsistencia.setOnClickListener { setAsistencia("$idplan") }
        binding.btnPagos.setOnClickListener{ startActivity(intent)}
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://57ac-189-174-165-86.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setAsistencia(idmesa:String){
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(MyApiMesas::class.java).setAsistenia("$idmesa")
            var body = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    if (body != null) {
                        toast("Asistencia guardada, Asistencia numero: ${body.id_asistencia}")
                        super.finish()
                    }
                }
            }
        }
    }

    /*override fun finish() {
        super.finish()
    }*/

}