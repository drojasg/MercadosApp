package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.mercados.R
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.databinding.ActivityPagosBinding
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.floor

class PagosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPagosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle = intent.extras
        val cantidad = bundle?.get("cantidad")
        val total = bundle?.get("total")
        val idproveedor = bundle?.get("idproveedor")
        println("el total es: $total")
        getExRate()
        getExEurRate()
        getExGbpRate()
        onRadioButtonClicked(binding.rgDivisas)

        printCantidad(cantidad as ArrayList<Int>)
        val pago = binding.etPago.text

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val realmonth = month + 1
        val year = c.get(Calendar.YEAR)
        val fecha = "$realmonth-$day-$year"

        binding.tvSaldoPendiente.text = total.toString()
        binding.btnPagar.setOnClickListener { doMath(cantidad, pago.toString().toFloat(), total as Float)
        setNewPago("$idproveedor", "$total", "$pago", "$fecha")}
    }

    fun printCantidad(cantidad : ArrayList<Int>){
        println(cantidad)
    }

    fun doMath(cantidad: ArrayList<Int>, _x : Float, total:Float){
        var b: Float
        var x = _x
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

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://d037-189-174-131-177.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setNewPago(id_proveedor:String, monto_total:String, pago:String, fecha_pago:String){
        CoroutineScope(Dispatchers.IO).launch {
            var call = getRetrofit().create(MyApiMesas::class.java).createPago("$id_proveedor",
                "$monto_total", "$pago", "$fecha_pago")
            var body = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    if(body != null){
                        val idpago = body.id_pago
                        toast("Pago guardado correctamente. $idpago")
                        setPagos("$idpago")
                    }
                }
                else{
                    toast("Ha ocurrido un error")
                }
            }
        }
    }

    private fun setPagos(idpago : String){
        toast("HOLIIIII-$idpago")
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
                        binding.tvtotalfinal.text = floor(total).toInt().toString()
                    }
                R.id.rbeur ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val euros = binding.tvTipoCambioEur.text.toString()
                        var total = pago.toFloat()*euros.toFloat()
                        binding.tvtotalfinal.text = floor(total).toInt().toString()
                    }
                R.id.rblib ->
                    if (checked){
                        val pago = binding.etPago.text.toString()
                        val libras = binding.tvTipoCambioGbp.text.toString()
                        val total = pago.toFloat()*libras.toFloat()
                        binding.tvtotalfinal.text = floor(total).toInt().toString()
                    }
            }
        }
    }
}