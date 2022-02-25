package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.SpinnerResponse
import com.example.mercados.databinding.ActivityAddNewMesaBinding
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class AddNewMesaActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddNewMesaBinding
    private lateinit var adapter: SpinnerAdapter
    private val mesasList = mutableListOf<SpinnerResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddNewMesaBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val spinner = binding.spnrMesas

        val locacion = prefs.getLocacion()

        getMesaSpinner(locacion)
        initSpiner()
        var idmesa = ""
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    idmesa = mesasList.get(p2).id_mesa
                    Log.d("AQUI ESTA EL ID!!", "$idmesa")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    mesasList.get(0).id_mesa
                }
            }

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val realmonth = month + 1
        val year = c.get(Calendar.YEAR)
        val fecha = "$realmonth-$day-$year"

        binding.btnAddPlan.setOnClickListener{ setNewPlan("$locacion", "$idmesa", "$fecha")}
    }

    private fun setNewPlan(locacion:String, idmesa:String, fecha:String) {
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(MyApiMesas::class.java).setMesaFromApp("$locacion", "$idmesa", "$fecha")
            var body = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    if (body != null){
                        toast("Nuevo plan creado correctamente")
                        super.finish()
                    }
                }
                else{
                    toast("Ha ocurrido un error")
                }
            }
        }
    }

    fun initSpiner(){
        adapter = SpinnerAdapter(this, mesasList)
        binding.spnrMesas.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://e1b0-189-174-127-179.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getMesaSpinner(idlocacion:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getMesaSpinner(idlocacion)
            val mesas = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    println(mesas)
                    val lista = mesas ?: emptyList()
                    mesasList.clear()
                    mesasList.addAll(lista)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun showError() {
        toast("Ha ocurrido un error!")
    }
}