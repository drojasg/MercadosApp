package com.example.mercados.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mercados.data.network.responses.LocacionResponse
import com.example.mercados.databinding.ItemMesasBinding
import com.example.mercados.ui.home.ui.main.AsistenciaActivity
import com.example.mercados.ui.home.ui.main.RecyclerViewClickListener

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

        holder.itemMesasBinding.cardView.setOnClickListener {

            listener.onRecyclerViewBtnClick(holder.itemMesasBinding.cardView, mesas[position])
            val idpplan = mesas[position].id_plan
            val idmesa = mesas[position].id_mesa
            val idproveedor = mesas[position].id_proveedor
            val artesano = mesas[position].artesano
            val giro = mesas[position].giro
            val fecha = mesas[position].fecha
            val locacion = mesas[position].locacion
            val renta = mesas[position].monto

            val toPass = Bundle()
            toPass.putString("idplan", idpplan)
            toPass.putString("idmesa", idmesa)
            toPass.putString("idproveedor", idproveedor)
            toPass.putString("artesano", artesano)
            toPass.putString("giro", giro)
            toPass.putString("fecha", fecha)
            toPass.putString("locacion", locacion)
            toPass.putString("renta", renta)

            val intent = Intent(context, AsistenciaActivity::class.java)
            intent.putExtras(toPass)
            context.startActivity(intent)

            //holder.itemMesasBinding.cardView.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#57A639"))
        }
    }

    inner class MesasViewHolder(
        val itemMesasBinding: ItemMesasBinding
    ) : RecyclerView.ViewHolder(itemMesasBinding.root)

}