package com.example.mercados.ui.home.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.EstadoDeCuentaResponse
import com.example.mercados.data.network.responses.PlanesSinPagoResponse
import com.example.mercados.databinding.ActivitySaldosBinding
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SaldosActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaldosBinding
    private lateinit var adapter: EstadoDeCuentaAdapter
    private val estadosList = mutableListOf<EstadoDeCuentaResponse>()
    private val planesList = mutableListOf<PlanesSinPagoResponse>()
    val myArray = arrayListOf<Int>()
    val myArray2 = arrayListOf<String>()
    val toPass = Bundle()
    var idprov = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySaldosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val bundle = intent.extras
        val idproveedor = bundle?.get("idproveedor")
        idprov = "$idproveedor"

        getEstadosCuenta("$idproveedor")
        initRecyclerView()
        refresh("$idproveedor")

        toPass.putString("idproveedor", idproveedor.toString())


        binding.btnPagar.setOnClickListener{
            val intent = Intent(this@SaldosActivity, PagosActivity::class.java)
            intent.putExtras(toPass)
            this.startActivity(intent)
        }
    }

    override fun onResume() {
        getEstadosCuenta("$idprov")
        super.onResume()
    }

    private fun initRecyclerView() {
        adapter = EstadoDeCuentaAdapter(this, estadosList)
        binding.rvEstadosDeCuenta.layoutManager = LinearLayoutManager(this)
        binding.rvEstadosDeCuenta.adapter = adapter
    }

    private fun getRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://smart-hotels-lead-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getEstadosCuenta(id_proveedor : String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getEstadoCuenta("$id_proveedor")
            val estadosDeCuenta = call.body()
            runOnUiThread{
                if (call.isSuccessful){
                    val lista = estadosDeCuenta ?: emptyList()
                    estadosList.clear()
                    estadosList.addAll(lista) //CHECAR LA SUMA DE LOS MONTOS!!! PARA QUE SE ACTUALICE!
                    adapter.notifyDataSetChanged()
                    prefs.saveTotal("${estadosList.sumOf { it.monto.toDouble()}}")
                    val total = prefs.getTotal()
                    println("$total")
                    toPass.putFloat("total",total.toFloat())
                    val pagado = estadosList.sumOf { it.monto_pago.toDouble() }
                    val totalReal = total.toDouble() - pagado
                    binding.tvSaldoP.text = totalReal.toString()


                    for(estado in estadosList) {
                        val cantidad = estado.monto

                        myArray.add(cantidad.toInt())

                        toPass.putIntegerArrayList("cantidad", myArray)
                    }

                    for(plan in estadosList){
                        var ultimo = plan.id_plan
                        myArray2.add(ultimo)
                        var ultimoP = myArray2.last()
                        toPass.putString("ultimoP", ultimoP)
                    }
                }
                else{
                    showError()
                }
            }
        }
    }

    private fun refresh(idproveedor: String){
        binding.swipeRefreshLayoutSaldos.setOnRefreshListener {
            getEstadosCuenta("$idproveedor")
            binding.swipeRefreshLayoutSaldos.isRefreshing = false
        }
    }

    private fun showError() {
        toast("Ha ocurrido un error")
    }

}