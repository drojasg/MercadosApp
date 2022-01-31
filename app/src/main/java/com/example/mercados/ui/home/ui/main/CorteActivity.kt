package com.example.mercados.ui.home.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mercados.databinding.ActivityCorteBinding

class CorteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCorteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCorteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
}