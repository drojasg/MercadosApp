package com.example.mercados.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mercados.R
import com.example.mercados.data.network.MyApiMesas
import com.example.mercados.data.network.responses.LocacionResponse
import com.example.mercados.databinding.ActivityMainBinding
import com.example.mercados.ui.home.ui.main.AddNewMesaActivity
import com.example.mercados.ui.home.ui.main.RecyclerViewClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MesasAdapter
    private val mesasList = mutableListOf<LocacionResponse>()
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locacion = intent.getStringExtra("id_locacion")
        getMesas("$locacion")

        initRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            getMesas("$locacion")
            adapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nuevo->{
                val intent = Intent(this, AddNewMesaActivity::class.java )
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        adapter = MesasAdapter(this, mesasList, this)
        binding.rvMesas.layoutManager = LinearLayoutManager(this)
        binding.rvMesas.adapter = adapter
    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://57ac-189-174-165-86.ngrok.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getMesas(id_locacion:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getMesas("$id_locacion")
            val mesas = call.body()
            runOnUiThread {
                if (call.isSuccessful){
                    val lista = mesas ?: emptyList()
                    mesasList.clear()
                    mesasList.addAll(lista)
                    Log.d("AQUI ESTA LA LLAMADA DEL API", "$mesas")
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onRecyclerViewBtnClick(view: View, mesa: LocacionResponse) {
        /*when(view.id){
            R.id.cardView ->{
                ColorStateList.valueOf(Color.parseColor("#57A639"))
            }
        }*/
    }

}