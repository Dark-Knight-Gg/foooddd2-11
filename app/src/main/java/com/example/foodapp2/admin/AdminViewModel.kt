package com.example.foodapp2.admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.foodapp2.event.Event

class AdminViewModel(application :Application) :AndroidViewModel(application) {
    private val _navigateToLogin = MutableLiveData<Event<Boolean>>()
    val navigateToLogin : MutableLiveData<Event<Boolean>> get() = _navigateToLogin
    private val _navigateToAddAdmin = MutableLiveData<Event<Boolean>>()
    val navigateToAddAdmin :MutableLiveData<Event<Boolean>> get() =_navigateToAddAdmin
    fun onClickBack(){
        _navigateToLogin.value = Event(true)
    }
    fun onClickAdd(){
        _navigateToAddAdmin.value = Event(true)
    }
}