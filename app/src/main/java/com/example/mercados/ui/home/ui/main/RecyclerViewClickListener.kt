package com.example.mercados.ui.home.ui.main

import android.view.View
import com.example.mercados.data.network.responses.LocacionResponse

interface RecyclerViewClickListener {
    fun onRecyclerViewBtnClick(view: View, mesa: LocacionResponse)
}