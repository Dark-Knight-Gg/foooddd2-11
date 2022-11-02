package com.example.foodapp2.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodapp2.database.Database
import com.example.foodapp2.event.Event
import com.example.foodapp2.model.User

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val database by lazy { Database.getInstance(application) }
    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username
    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> get() = _password
    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: MutableLiveData<String> get() = _repeatPassword
    private val _back = MutableLiveData<Event<Boolean>>()
    val back: LiveData<Event<Boolean>> get() = _back
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user
    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin: LiveData<Event<Boolean>> get() = _navigateToLogin
    private val _showMessage = MutableLiveData<Event<String>>()
    val showMessage: LiveData<Event<String>> get() = _showMessage
    fun onClickRegister() {
        val username = _username.value
        val password = _password.value
        val repeatPassword = _repeatPassword.value
        if (!username?.let { it1 -> database.isUsernameExists(it1) }!!) {
            if (password == repeatPassword) {
                if (password != null) {
                    database.insertIntoUsers(username, password)
                    _navigateToLogin.value = Event(true)
                    return
                }

            } else {
                _showMessage.value = Event("Nhắc lại mật khẩu không đúng!")
            }
        } else {
            _showMessage.value = Event("Sai tên đăng nhập hoặc mật khẩu!")
        }
    }

    fun onClickBack() {
        _back.value = Event(true)
    }

}