package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.mercados.R
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.databinding.ActivityPagosBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class PagosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPagosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle = intent.extras
        val cantidad = bundle?.get("cantidad")
        val total = bundle?.get("total")
        println("el total es: $total")
        getExRate()
        getExEurRate()
        getExGbpRate()
        onRadioButtonClicked(binding.rgDivisas)

        printCantidad(cantidad as ArrayList<Int>)
        val pago = binding.etPago.text

        binding.tvSaldoPendiente.text = total.toString()
        binding.btnPagar.setOnClickListener { doMath(cantidad, pago.toString().toFloat(), total as Float) }
    }

    fun printCantidad(cantidad : ArrayList<Int>){
        println(cantidad)
    }

    fun doMath(cantidad: ArrayList<Int>, _x : Float, total:Float){
        var b: Float
        var x = _x
        var c : Float
        var myArray = arrayListOf<Float>()
        for(n in cantidad){
            if (x >= n){
                b = x - n
                x = b
                myArray.add(n.toFloat())
                println("Aqui dentro del primer if agrego el $n")
            }
            else if ( x < n && x > 0){
                b = x - n
                myArray.add(x)
                x = b
                println("Aqui dentro del elif agrego el: $x")
                if (x <= 0){

                }
            }
        }
        println(myArray)

    }

    fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://57ac-189-174-165-86.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getExRate(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getCurrentRate()
            val exRate = call.body()
            runOnUiThread {
                if (exRate != null) {
                    binding.tvTipoCambio.text = exRate.CurrentRate
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
                    binding.tvTipoCambioEur.text = exRate.CurrentRate
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
                    binding.tvTipoCambioGbp.text = exRate.CurrentRate
                }
            }
        }
    }

    fun onRadioButtonClicked(view: View){
        if (view is RadioButton){
            val checked = view.isChecked

            when(view.getId()){
                R.id.rbusd ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val total = pago.toInt() * 1
                        binding.tvtotalfinal.text = total.toString()
                    }
                R.id.rbpesos ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val pesos = binding.tvTipoCambio.text.toString()
                        val total = pago.toFloat()/pesos.toFloat()
                        binding.tvtotalfinal.text = total.toString()
                    }
                R.id.rbeur ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val euros = binding.tvTipoCambioEur.text.toString()
                        val total: Float = pago.toFloat()*euros.toFloat()
                        binding.tvtotalfinal.text = total.toString()
                    }
                R.id.rblib ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val libras = binding.tvTipoCambioGbp.text.toString()
                        val total = pago.toFloat()*libras.toFloat()
                        binding.tvtotalfinal.text = total.toString()
                    }
            }
        }
    }
}