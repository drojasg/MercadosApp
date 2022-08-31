package com.example.mercados.ui.home.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.get
import com.example.mercados.R
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.databinding.ActivityInfoLocacionBinding
import com.example.mercados.ui.home.MainActivity
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class InfoLocacionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoLocacionBinding
    private lateinit var clima: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoLocacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locacion = intent.getStringExtra("id_locacion")
        val ocupacion = binding.etOcupacion.text

        val spinner = binding.spnrClima
        ArrayAdapter.createFromResource(
            this,
            R.array.clima,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 != null) {
                    clima = p0.getItemAtPosition(p2).toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                if (p0 != null) {
                    clima = p0.getItemAtPosition(0).toString()
                }
            }
        }

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val realmonth = month + 1
        val year = c.get(Calendar.YEAR)
        val fecha = "$realmonth-$day-$year"

        binding.btnGuardarInfo.setOnClickListener { createInfoLocacion("$locacion", "$ocupacion", "$clima", "$fecha") }
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://shiny-roses-call-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createInfoLocacion(id_locacion:String, ocupacion:String, clima: String,
    fecha:String){
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(MyApiMesas::class.java).createInfoLocacion("$id_locacion", "$ocupacion", "$clima", "$fecha")
            var body = call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    if(body != null){
                        toast("Info guardada correctamente")
                        val intent = Intent(this@InfoLocacionActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                else{
                    toast("Ha ocurrido un error")
                }
            }
        }
    }
}