package com.example.mercados.ui.home.ui.main

import android.app.Application
import com.bumptech.glide.Glide.init

class Mercados : Application() {

    companion object{
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}