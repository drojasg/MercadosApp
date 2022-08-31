package com.example.mercados.ui.home.ui.main

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.databinding.adapters.AdapterViewBindingAdapter
import com.example.mercados.R
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.JustificacionesSpinnerResponse
import com.example.mercados.databinding.ActivityAsistenciaBinding
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AsistenciaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsistenciaBinding
    private lateinit var adapter: SpinnerJustificacionesAdapter
    private val justificacionesList = mutableListOf<JustificacionesSpinnerResponse>()

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
        val asistencia = bundle?.get("asistencia")
        val falta = bundle?.get("falta")

        binding.idplan.text = (idplan as CharSequence?)!!
        binding.idmesa.text = (idmesa as CharSequence?)!!
        binding.idproveedor.text = (idproveedor as CharSequence?)!!
        binding.artesano.text = (artesano as CharSequence?)!!
        binding.giro.text = (giro as CharSequence?)!!
        binding.fecha.text = (fecha as CharSequence?)!!
        binding.locacion.text = (locacion as CharSequence?)!!
        binding.renta.text = renta as CharSequence?

        if(asistencia == "1"){
            binding.btnAsistencia.isVisible = false
            binding.btnFalta.isVisible = false

        }
        else if(falta == "1"){
            binding.btnAsistencia.isVisible = false
            binding.btnFalta.isVisible = false
        }

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

        var estadoAsistencia = 1
        var estadoFalta = 0

        val spinner = binding.spnrJustificaciones
        getJustificacionesSpinner()
        initSpinner()

        var idjustificacion = ""
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    idjustificacion = justificacionesList.get(p2).id_justificacion
                }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                justificacionesList.get(0).id_justificacion
            }

            }

        binding.btnAsistencia.setOnClickListener { setAsistencia("$idplan", "$estadoAsistencia", "$idjustificacion") }
        binding.btnFalta.setOnClickListener { setAsistencia("$idplan", "$estadoFalta", "$idjustificacion") }
        binding.btnPagos.setOnClickListener{ startActivity(intent)}

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://shiny-roses-call-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun initSpinner(){
        adapter = SpinnerJustificacionesAdapter(this, justificacionesList)
        binding.spnrJustificaciones.adapter = adapter
    }

    private fun getJustificacionesSpinner(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getJustificaciones()
            val justificaciones = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    val lista = justificaciones ?: emptyList()
                    justificacionesList.clear()
                    justificacionesList.addAll(lista)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun setAsistencia(idplan:String, estado: String, idjustificacion: String){
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(MyApiMesas::class.java).setAsistenia("$idplan", "$estado", "$idjustificacion")
            var body = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    if (body != null) {
                        if (body.estado == "0"){
                            toast("Falta registrada de manera exitosa")
                            super.finish()
                        }
                        else if(body.estado == "1"){
                            toast("Asistencia guardada, Asistencia numero: ${body.id_asistencia}")
                            super.finish()
                        }
                    }
                }
            }
        }
    }

    private fun showError() {
        toast("Ha ocurrido un error!")
    }
}