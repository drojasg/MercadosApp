package com.example.mercados.ui.home.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mercados.R
import com.example.mercados.data.network.responses.SpinnerResponse
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import kotlinx.coroutines.selects.select

class SpinnerAdapter internal constructor(internal var context: Context, internal var list: List<SpinnerResponse>):BaseAdapter(){

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View{
        var view = view
        if(view == null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_spinner_mesa, viewGroup, false)
        }

        val textView = view!!.findViewById<TextView>(R.id.tvSpinner)
        textView.text = list[i].id_mesa + " - " + list[i].mesa

        return textView;
    }


}