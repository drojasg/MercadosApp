package com.example.mercados.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.mercados.R
import com.example.mercados.databinding.ActivityLoginBinding
import com.example.mercados.ui.home.ScanLocacionActivity
import com.example.mercados.util.hide
import com.example.mercados.util.show
import com.example.mercados.util.toast


class LoginActivity : AppCompatActivity(), AuthListener {

    private lateinit var bindingv: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingv = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingv.root)
        val binding : ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

    }

    override fun onStarted() {
        bindingv.progressBar.show()
    }

    override fun onSuccess(status: String ) {
        toast("${status}: Todo Bien")
        val intent = Intent(this@LoginActivity, ScanLocacionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onFailure(message: String) {
        bindingv.progressBar.hide()
        toast(message)
    }
}