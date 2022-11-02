package com.example.foodapp2.login

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodapp2.database.Database
import com.example.foodapp2.event.Event
import com.example.foodapp2.model.User

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val database by lazy { Database.getInstance(application) }
    private val sharedPreferences by lazy {
        application.getSharedPreferences("Data", Context.MODE_PRIVATE)
    }

    private val _checked = MutableLiveData<Boolean>()
    val checked: MutableLiveData<Boolean> get() = _checked

    private val _username = MutableLiveData<String>()
    val username: MutableLiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: MutableLiveData<String> get() = _password

    private val _navigateToAdmin = MutableLiveData<Event<Boolean>>()
    val navigateToAdmin: LiveData<Event<Boolean>> get() = _navigateToAdmin

    private val _navigateToRegister = MutableLiveData<Event<Boolean>>()
    val navigateToRegister: LiveData<Event<Boolean>> get() = _navigateToRegister

    private val _navigateToClient = MutableLiveData<Event<Boolean>>()
    val navigateToClient: LiveData<Event<Boolean>> get() = _navigateToClient

    private val _showMessage = MutableLiveData<Event<String>>()
    val showMessage: LiveData<Event<String>> get() = _showMessage

    init {
        handlePrefillData()
    }

    private fun handlePrefillData() {
        val isChecked = sharedPreferences.getBoolean("Checked", false)
        if (isChecked) {
            val userName = sharedPreferences.getString("Username", "")
            val password = sharedPreferences.getString("Password", "")
            _username.value = userName ?: ""
            _password.value = password ?: ""
        } else {
            _username.value = ""
            _password.value = ""
        }
        _checked.value = isChecked
    }

    fun onClickLogin() {
        val usernameStr = username.value ?: ""
        val passwordStr = password.value ?: ""
        val isSuccess: Boolean = database.isLogin(User(usernameStr, passwordStr))
        if (usernameStr == "Admin" && passwordStr == "admin123") {
            _navigateToAdmin.value = Event(true)
            return
        }
        if (isSuccess) {
            handleSaveLoginInfo(usernameStr, passwordStr)
            _showMessage.value = Event("Đăng nhập thành công!")
            _navigateToClient.value = Event(true)
        } else {
            _showMessage.value = Event("Sai thông tin đăng nhập!")
        }
    }

    fun onClickRegister() {
        val flag = true
        _navigateToRegister.value = Event(flag)
    }

    private fun handleSaveLoginInfo(usernameStr: String, passwordStr: String) {
        val isSavePasswordChecked = checked.value ?: false
        if (isSavePasswordChecked) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("Username", usernameStr)
            editor.putString("Password", passwordStr)
            editor.putBoolean("Checked", true)
            editor.apply()
        } else {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.remove("Username")
            editor.remove("Password")
            editor.remove("Checked")
            editor.apply()
        }
    }
}