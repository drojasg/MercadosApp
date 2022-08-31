package com.example.mercados.ui.auth

import androidx.lifecycle.LiveData
import java.util.*

interface AuthListener {
    fun onStarted()
    fun onSuccess(status: String)
    //fun onSuccess2(usercloud: String)
    fun onFailure(message: String)
}