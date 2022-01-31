package com.example.mercados.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onSuccess(status: String)
    fun onFailure(message: String)
}