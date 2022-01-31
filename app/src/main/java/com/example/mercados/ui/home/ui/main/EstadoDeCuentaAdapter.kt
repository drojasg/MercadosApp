package com.example.mercados.ui.home.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercados.R
import com.example.mercados.data.network.responses.EstadoDeCuentaResponse
import com.example.mercados.databinding.ActivityPagosBinding
import com.example.mercados.databinding.ItemPagosBinding
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.google.zxing.ResultMetadataType
import java.lang.Double.sum

class EstadoDeCuentaAdapter(
    private val context: Context,
    val estadosDeCuenta: List<EstadoDeCuentaResponse>,
    ): RecyclerView.Adapter<EstadoDeCuentaAdapter.EstadosDeCuentaViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadosDeCuentaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPagosBinding.inflate(inflater, parent, false)
        return EstadosDeCuentaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EstadosDeCuentaViewHolder, position: Int) {
        val estadoDeCuenta = estadosDeCuenta[position]

        holder.itemPagosBinding.tvGiroEdC.text = estadoDeCuenta.giro
        holder.itemPagosBinding.tvArtesanoEdC.text = estadoDeCuenta.artesano
        holder.itemPagosBinding.tvRentaEdC.text = estadoDeCuenta.monto.toString()
        holder.itemPagosBinding.tvLocacionEdC.text = estadoDeCuenta.locacion
        holder.itemPagosBinding.tvFechaEdC.text = estadoDeCuenta.fecha

        /*var total = estadosDeCuenta.sumOf { it.cantidad.toDouble() }
        prefs.saveTotal("${total}")*/
    }

    override fun getItemCount() = estadosDeCuenta.size

    inner class EstadosDeCuentaViewHolder(
        val itemPagosBinding: ItemPagosBinding,
    ) : RecyclerView.ViewHolder(itemPagosBinding.root)
}