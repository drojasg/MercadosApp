package com.example.mercados.ui.home

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.GREEN
import android.hardware.camera2.params.ColorSpaceTransform
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.graphics.toColor
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
import com.bumptech.glide.load.engine.executor.GlideExecutor.UncaughtThrowableStrategy.LOG
import com.example.mercados.data.network.responses.AsistenciasResponse
import com.example.mercados.ui.home.ui.main.CorteActivity
import com.example.mercados.ui.home.ui.main.CorteReviewActivity
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.toast

class MainActivity : AppCompatActivity(), RecyclerViewClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MesasAdapter
    private val mesasList = mutableListOf<LocacionResponse>()
    private val asistenciasList = mutableListOf<AsistenciasResponse>()
    var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val locacion = prefs.getLocacion()
        getMesas("$locacion")
        getAsistencias()

        initRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            getMesas("$locacion")
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nuevo -> {
                val intent = Intent(this, AddNewMesaActivity::class.java)
                startActivity(intent)
            }
            R.id.corte -> {
                val intent = Intent(this, CorteActivity::class.java)
                startActivity(intent)
            }
            R.id.infocorte -> {
                val intent = Intent(this, CorteReviewActivity::class.java)
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

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://shiny-roses-call-189-174-83-173.loca.lt/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getMesas(id_locacion: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getMesas("$id_locacion")
            val mesas = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val lista = mesas ?: emptyList()
                    mesasList.clear()
                    mesasList.addAll(lista)
                    if (mesasList.isEmpty()) {
                    } else {
                        prefs.saveIdPlan("${mesasList[0].id_plan}")
                    }

                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun getAsistencias() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(MyApiMesas::class.java).getAsistencia()
            val asistencias = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val lista = asistencias ?: emptyList()
                    asistenciasList.clear()
                    asistenciasList.addAll(lista)
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