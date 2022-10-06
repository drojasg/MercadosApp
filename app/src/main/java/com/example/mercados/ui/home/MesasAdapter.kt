package com.example.mercados.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercados.data.network.responses.LocacionResponse
import com.example.mercados.databinding.ItemMesasBinding
import com.example.mercados.ui.home.ui.main.AsistenciaActivity
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.ui.home.ui.main.RecyclerViewClickListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MesasAdapter(
    private val context: Context,
    val mesas:List<LocacionResponse>,
    private val listener:RecyclerViewClickListener
    ): RecyclerView.Adapter<MesasAdapter.MesasViewHolder>(){

    override fun getItemCount() = mesas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMesasBinding.inflate(inflater, parent, false)

        return MesasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MesasViewHolder, position: Int) {
        val mesa = mesas[position]

        holder.itemMesasBinding.tvIdPlan.text = mesa.id_plan
        holder.itemMesasBinding.tvIdMesa.text = mesa.id_mesa
        holder.itemMesasBinding.tvArtesano.text = mesa.artesano
        holder.itemMesasBinding.tvGiro.text = mesa.giro
        holder.itemMesasBinding.tvFecha.text = mesa.fecha
        holder.itemMesasBinding.tvLocacion.text = mesa.locacion
        holder.itemMesasBinding.tvRenta.text = mesa.monto

        if(mesa.asistencia == "1" && mesa.falta == "0"){
            holder.itemMesasBinding.cvMesas.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#308446"))
        }
        else if(mesa.falta == "1" && mesa.asistencia == "0"){
            holder.itemMesasBinding.cvMesas.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E83845"))
        }
        else{
            holder.itemMesasBinding.cvMesas.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
        }

        holder.itemMesasBinding.cvMesas.setOnClickListener {

            listener.onRecyclerViewBtnClick(holder.itemMesasBinding.cvMesas, mesas[position])
            val idplan = mesas[position].id_plan
            val idmesa = mesas[position].id_mesa
            val idproveedor = mesas[position].id_proveedor
            val artesano = mesas[position].artesano
            val giro = mesas[position].giro
            val fecha = mesas[position].fecha
            val locacion = mesas[position].locacion
            val renta = mesas[position].monto
            val asistencia = mesas[position].asistencia
            val falta = mesas[position].falta

            val toPass = Bundle()
            toPass.putString("idplan", idplan)
            toPass.putString("idmesa", idmesa)
            toPass.putString("idproveedor", idproveedor)
            toPass.putString("artesano", artesano)
            toPass.putString("giro", giro)
            toPass.putString("fecha", fecha)
            toPass.putString("locacion", locacion)
            toPass.putString("renta", renta)
            toPass.putString("asistencia", asistencia)
            toPass.putString("falta", falta)

            val intent = Intent(context, AsistenciaActivity::class.java)
            intent.putExtras(toPass)
            context.startActivity(intent)
        }
    }

    inner class MesasViewHolder(
        val itemMesasBinding: ItemMesasBinding
    ) : RecyclerView.ViewHolder(itemMesasBinding.root)
}