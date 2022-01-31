package com.example.mercados.ui.home.ui.main

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "Mydtb"
    val SHARED_LOCACION = "locacion"
    val SHARED_TOTAL = "saldoP"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)
    val storage1 = context.getSharedPreferences(SHARED_TOTAL, 0)

    fun saveLocacion(locacion:String){
        storage.edit().putString(SHARED_LOCACION, locacion).apply()
    }

    fun getLocacion():String{
        return storage.getString(SHARED_LOCACION, "")!!
    }

    fun saveTotal(total:String){
        storage1.edit().putString(SHARED_TOTAL, total).apply()
    }

    fun cleanTotal(){
        storage1.edit().clear().apply()
    }

    fun getTotal():String{
        return storage1.getString(SHARED_TOTAL, "")!!
    }
}