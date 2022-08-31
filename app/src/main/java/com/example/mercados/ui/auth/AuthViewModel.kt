package com.example.mercados.ui.auth

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mercados.data.network.responses.AuthResponse
import com.example.mercados.data.repositories.UserRepository
import com.example.mercados.ui.home.ui.main.Mercados.Companion.prefs
import com.example.mercados.util.ApiException
import com.example.mercados.util.Coroutines

class AuthViewModel: ViewModel(){
    var user: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(user.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid user or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = UserRepository().userLogin(user!!, password!!)

                authResponse.status?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                prefs.clearUsercloud()
                prefs.saveUsercloud(authResponse.usercloud!!)
                authListener?.onFailure(authResponse.error!!)

            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }

        }

    }
}