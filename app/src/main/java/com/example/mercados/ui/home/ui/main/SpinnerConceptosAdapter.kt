package com.example.mercados.ui.home.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mercados.R
import com.example.mercados.data.network.responses.ConceptosSpinnerResponse

class SpinnerConceptosAdapter internal constructor(internal var context: Context,
                                                   internal var list: MutableList<ConceptosSpinnerResponse>
):BaseAdapter(){
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (view == null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.item_spinner_concepto, p2, false)
        }
        val textView = view!!.findViewById<TextView>(R.id.tvSpinnerConceptos)
        textView.text = list[p0].id_concepto + " - " + list[p0].concepto

        return textView
    }
}