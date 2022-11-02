package com.example.foodapp2.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.foodapp2.R
import com.example.foodapp2.databinding.ActivityRegisterBinding
import com.example.foodapp2.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.registerViewModel = registerViewModel
        onClickRegister()
        onClickBack()
    }

    //    private fun initListener() {
//        binding.registerImgBack.setOnClickListener {
//            intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//        }
//        binding.registerBtnRegister.setOnClickListener {
//            val username = binding.registerEdtUsername.text.toString()
//            val password = binding.registerEdtPassword.text.toString()
//            val repeatPassword = binding.registerEdtRepeatPassword.text.toString()
//            if (!database.isUsernameExists(username)) {
//                if (password == repeatPassword) {
//                    database.insertIntoUsers(username, password)
//                    intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Nhắc lại mật khẩu chưa đúng!", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    private fun onClickRegister() {
        registerViewModel.navigateToLogin.observe(this) {
           it.getContentIfNotHandled()?.let{
               intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)
           }

        }
        registerViewModel.showMessage.observe(this) {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onClickBack() {
        registerViewModel.back.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }
}