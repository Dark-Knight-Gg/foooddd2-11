package com.example.foodapp2.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foodapp2.R
import com.example.foodapp2.admin.AdminActivity
import com.example.foodapp2.client.ClientActivity
import com.example.foodapp2.databinding.ActivityLoginBinding
import com.example.foodapp2.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
        loginViewModel.navigateToRegister.observe(this) {
            it.getContentIfNotHandled()?.let {
                intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        loginViewModel.navigateToClient.observe(this) {
            it.getContentIfNotHandled()?.let{
                intent = Intent(this, ClientActivity::class.java)
                startActivity(intent)
            }

        }
        loginViewModel.navigateToAdmin.observe(this) {
            it.getContentIfNotHandled()?.let{intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)}

        }

        loginViewModel.showMessage.observe(this) {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

        }
    }
}